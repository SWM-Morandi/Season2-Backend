package kr.co.morandi.backend.defense_record.domain.model.dailydefense_record;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.factory.TestBaekjoonSubmitFactory;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class DailyRecordTest {

    @DisplayName("시험 기록(Record)를 종료하면 종료 상태로 변경된다.")
    @Test
    void terminateDefense() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);


        // when
        final boolean result = dailyRecord.terminteDefense();

        // then
        assertThat(result).isTrue();

    }

    @DisplayName("시험 기록(Record)가 종료된 상태에서 다시 종료하려고 하면 false를 반환한다.")
    @Test
    void terminateDefenseWhenAlreadyTerminated() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);
        dailyRecord.terminteDefense();

        // when & then
        assertThatThrownBy(() -> dailyRecord.terminteDefense())
                .isInstanceOf(MorandiException.class);
    }
    @DisplayName("오늘의 문제를 정답처리 하면 푼 total 문제 수가 증가하고, 푼 시간이 기록된다.")
    @Test
    void solveProblem() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);

        // when
        BaekjoonSubmit 제출 = TestBaekjoonSubmitFactory.createSubmit(member, dailyRecord.getDetail(2L), LocalDateTime.of(2024, 3, 1, 12, 15, 0));
        제출.trySolveProblem();

        // then
        assertThat(dailyRecord)
                .extracting("totalSolvedTime", "totalSolvedCount")
                .contains(
                        15 * 60L, 1L
                );
        assertThat(dailyRecord.getDetails()).hasSize(1)
                .extracting("isSolved", "solvedTime")
                .contains(
                        tuple(true, 15 * 60L)
                );
    }
    @DisplayName("이미 정답처리된 문제를 정답 solved하려하면 바뀌지 않는다.")
    @Test
    void solveProblemWhenAlreadySolved() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);

        BaekjoonSubmit 제출 = TestBaekjoonSubmitFactory.createSubmit(member, dailyRecord.getDetail(2L), LocalDateTime.of(2024, 3, 1, 12, 15, 0));
        제출.trySolveProblem();

        // when
        BaekjoonSubmit 제출2 = TestBaekjoonSubmitFactory.createSubmit(member, dailyRecord.getDetail(2L), LocalDateTime.of(2024, 3, 1, 12, 20, 0));
        제출2.trySolveProblem();


        // then
        assertThat(dailyRecord)
                .extracting("totalSolvedTime", "totalSolvedCount")
                .contains(
                        15 * 60L, 1L
                );
        assertThat(dailyRecord.getDetails()).hasSize(1)
                .extracting("isSolved", "solvedTime")
                .contains(
                        tuple(true, 15 * 60L)
                );
    }
    @DisplayName("풀어낸 문제들에 대한 문제번호 목록을 반환할 수 있다.")
    @Test
    void getSolvedProblemNumbers() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);
        dailyRecord.tryMoreProblem(getProblems(dailyDefense, 3L));

        BaekjoonSubmit 제출 = TestBaekjoonSubmitFactory.createSubmit(member, dailyRecord.getDetail(2L), LocalDateTime.of(2024, 3, 1, 12, 15, 0));
        제출.trySolveProblem();

        // when
        final Set<Long> solvedProblemNumbers = dailyRecord.getSolvedProblemNumbers();


        // then
        assertThat(solvedProblemNumbers).hasSize(1)
                .contains(2L);

    }

    @DisplayName("시험에 응시하면 오늘의 문제 attemptCount가 1 증가한다.")
    @Test
    void increaseAttempCountWhenTryDefense() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);

        // when
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);

        // then
        assertThat(dailyDefense.getAttemptCount()).isEqualTo(1L);

    }

    @DisplayName("오늘의 문제 기록에서 세부 문제의 정답 여부를 확인할 수 있다.")
    @Test
    void isSolvedProblem() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);

        // when
        final boolean solvedProblem = dailyRecord.isSolvedProblem(2L);

        // then
        assertThat(solvedProblem).isFalse();

    }
    @DisplayName("오늘의 문제 기록이 이미 있을 때, 같은 문제를 다시 시도하면 기존 문제 기록을 반환한다.")
    @Test
    void tryExistDetailThenReturnExistDetail() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, triedProblem);

        // when
        dailyRecord.tryMoreProblem(triedProblem);

        // then
        assertThat(dailyRecord.getDetails()).hasSize(1)
                .extracting("problem")
                .contains(triedProblem.get(2L));
    }

    @DisplayName("오늘의 문제 기록이 만들어졌을 때 푼 문제 수는 0문제 이어야 한다.")
    @Test
    void solvedCountIsZero() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getTotalSolvedCount()).isZero();
    }

    @DisplayName("오늘의 문제 기록이 만들어진 시점이 문제가 출제된 시점에서 하루 이상 넘어가면 예외가 발생한다.")
    @Test
    void recordCreateExceptionWhenOverOneDay() {
        // given
        LocalDate createdDate = LocalDate.of(2024, 3, 1);
        DailyDefense DailyDefense = createDailyDefense(createdDate);

        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(DailyDefense, 2L);

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 2, 0, 0, 0);

        // when & then
        assertThatThrownBy(() -> DailyRecord.tryDefense(startTime, DailyDefense, member, problems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("오늘의 문제 기록은 출제 날짜와 같은 날에 생성되어야 합니다.");
    }
    @DisplayName("오늘의 문제 기록이 만들어진 시점이 문제가 출제된 시점에서 하루 이상 넘어가지 않으면 정상적으로 등록된다.")
    @Test
    void recordCreatedWithinOneDay() {
        // given
        LocalDate createdDate = LocalDate.of(2024, 3, 1);
        DailyDefense dailyDefense = createDailyDefense(createdDate);

        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 23, 59, 59);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // then
        assertNotNull(dailyDefenseRecord);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 정답 여부는 모두 오답 상태여야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getDetails())
                .extracting("isSolved")
                .contains(false);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 제출 횟수는 모두 0회여야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getDetails())
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 정답 제출은 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(dailyDefense,2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getDetails())
                .extracting("correctSubmitId")
                .contains((String)null);
    }
    private Map<Long, Problem> getProblems(DailyDefense DailyDefense, Long problemNumber) {
        return DailyDefense.getDailyDefenseProblems().stream()
                .filter(p -> p.getProblemNumber().equals(problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }
    private DailyDefense createDailyDefense() {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        LocalDate createdDate = LocalDate.of(2024, 3, 1);
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap);
    }
    private DailyDefense createDailyDefense(LocalDate createdDate) {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return List.of(problem1, problem2, problem3);
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
}