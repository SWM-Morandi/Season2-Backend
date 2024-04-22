package kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DailyRecordRepositoryTest {

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("특정 시점 DailyRecord의 순위를 조회할 수 있다.")
    @Test
    void getDailyRecordsRankByDate() {
        // given
        LocalDate today = LocalDate.of(2021, 10, 1);
        final DailyDefense dailyDefense = createDailyDefense(today);

        final Member member1 = createMember("userA", "userA");
        final Member member2 = createMember("userB", "userB");
        final Member member3 = createMember("userC", "userC");

        final DailyRecord dailyRecord1 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member1, getProblem(dailyDefense, 1L));
        final DailyRecord dailyRecord2 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member2, getProblem(dailyDefense, 2L));
        final DailyRecord dailyRecord3 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member3, getProblem(dailyDefense, 3L));

        /*
        * member1: 한 문제 해결 일찍
        * member2 : 두 문제 해결
        * member3: 한 문제 해결 1보다 늦게
        *
        * -> 등수 = 2 -> 1 -> 3
        * */
        dailyRecord1.solveProblem(1L, "exampleCode", LocalDateTime.of(2021, 10, 1, 0, 15));

        dailyRecord2.solveProblem(2L, "exampleCode", LocalDateTime.of(2021, 10, 1, 0, 30));
        dailyRecord2.tryMoreProblem(getProblem(dailyDefense, 3L));
        dailyRecord2.solveProblem(3L, "exampleCode", LocalDateTime.of(2021, 10, 1, 0, 45));

        dailyRecord3.solveProblem(3L, "exampleCode", LocalDateTime.of(2021, 10, 1, 1, 0));

        dailyRecordRepository.saveAll(List.of(dailyRecord1, dailyRecord2, dailyRecord3));


        // when
        Pageable pageable = PageRequest.of(0, 5);
        Page<DailyRecord> dailyRecords = dailyRecordRepository.getDailyRecordsRankByDate(today, pageable);

        // then
        assertThat(dailyRecords).hasSize(3)
                .extracting(DailyRecord::getMember, DailyRecord::getSolvedCount, DailyRecord::getTotalSolvedTime)
                .containsExactly(// 푼 시간은 초단위
                        tuple(member2, 2L, 75L * 60),
                        tuple(member1, 1L, 15L * 60),
                        tuple(member3, 1L, 60L * 60)
                );

    }

    @DisplayName("원하는 recordId에 해당하는 DailyRecord가 존재할 때 찾아올 수 있다.")
    @Test
    void findDailyRecordWithRecordId() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();
        final DailyRecord dailyRecord = tryDailyDefense(today, member);

        // when
        Optional<DailyRecord> maybeDailyRecord = dailyRecordRepository.findDailyRecordByRecordId(member, dailyRecord.getRecordId(), today.toLocalDate());

        // then
        assertThat(maybeDailyRecord).isPresent()
                .get()
                .extracting("testDate", "problemCount")
                .contains(today, 1);

    }


    // TODO fetch join이 정상적으로 되는지 확인하는 테스트코드 작성
    @DisplayName("오늘 날짜에 해당하는 DailyRecord가 존재할 때 문제 리스트까지 함께 가져올 수 있다.")
    @Test
    void findDailyRecordByMemberAndDateWithFetchJoin() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();
        tryDailyDefense(today, member);

        // when
        DailyRecord dailyRecord = dailyRecordRepository.findDailyRecordByMemberAndDate(member, today.toLocalDate())
                .orElse(null);

        // then
        assertThat(dailyRecord).isNotNull();
        assertThat(dailyRecord.getDetails()).hasSize(1)
                .extracting("problemNumber","problem.baekjoonProblemId")
                .contains(
                        tuple(2L,2L)
                );
    }
    @DisplayName("오늘 날짜에 해당하는 DailyRecord가 존재할 때 찾아올 수 있다.")
    @Test
    void findDailyRecordByMemberAndDate() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();
        tryDailyDefense(today, member);

        // when
        Optional<DailyRecord> maybeDailyRecord = dailyRecordRepository.findDailyRecordByMemberAndDate(member, today.toLocalDate());

        // then
        assertThat(maybeDailyRecord).isPresent()
                .get()
                .extracting("testDate", "problemCount")
                .contains(today, 1);

    }

    private DailyRecord tryDailyDefense(LocalDateTime today, Member member) {
        final DailyDefense dailyDefense = createDailyDefense(today.toLocalDate());

        DailyRecord dailyRecord = DailyRecord.tryDefense(today, dailyDefense, member, getProblem(dailyDefense, 2L));
        return dailyRecordRepository.save(dailyRecord);
    }

    private Map<Long, Problem> getProblem(DailyDefense dailyDefense, Long problemNumber) {
        return dailyDefense.getDailyDefenseProblems().stream()
                .filter(problem -> Objects.equals(problem.getProblemNumber(), problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }

    private DailyDefense createDailyDefense(LocalDate createdDate) {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return dailyDefenseRepository.save(DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap));
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);

        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember() {
        return memberRepository.save(Member.create("test", "test" + "@gmail.com", GOOGLE, "test", "test"));
    }
    private Member createMember(String nickname, String email) {
        return memberRepository.save(Member.create(nickname, email + "@gmail.com", GOOGLE, "test", "test"));
    }

}