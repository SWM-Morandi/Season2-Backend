package kr.co.morandi.backend.infrastructure.persistence.record.dailyrecord;

import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefenseProblem;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.model.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@ActiveProfiles("test")
class DailyRecordRepositoryTest {

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;


    @AfterEach
    void tearDown() {
        dailyRecordRepository.deleteAll();
        dailyDefenseProblemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
        dailyRecordRepository.deleteAllInBatch();
        problemRepository.deleteAll();
        memberRepository.deleteAll();
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

}