package kr.co.morandi.backend.domain.record.dailydefense;

import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
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
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defense.model.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
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

    @AfterEach
    void tearDown() {
        dailyRecordRepository.deleteAll();
        dailyDefenseRepository.deleteAll();
        problemRepository.deleteAll();
        memberRepository.deleteAll();
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
        Optional<DailyRecord> foundDailyRecord = dailyRecordRepository.findDailyRecordByMemberAndDate(member, today.toLocalDate());

        // then
        assertThat(foundDailyRecord).isPresent()
                .get()
                .extracting("testDate", "problemCount")
                .contains(today, 1);

    }

    private void tryDailyDefense(LocalDateTime today, Member member) {
        final DailyDefense dailyDefense = createDailyDefense(today.toLocalDate());
        dailyDefenseRepository.save(dailyDefense);

        DailyRecord dailyRecord = DailyRecord.tryDefense(today, dailyDefense, member, getProblem(dailyDefense, 2L));
        dailyRecordRepository.save(dailyRecord);
    }

    private Map<Long, Problem> getProblem(DailyDefense dailyDefense, Long problemNumber) {
        return dailyDefense.getDailyDefenseProblems().stream()
                .filter(problem -> Objects.equals(problem.getProblemNumber(), problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }

    private DailyDefense createDailyDefense(LocalDate createdDate) {
        List<Problem> problems = createProblems();
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problems);
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