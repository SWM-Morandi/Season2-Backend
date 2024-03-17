package kr.co.morandi.backend.domain.record.dailydefense;

import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defense.model.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class DailyRecordTest {

    @DisplayName("오늘의 문제 기록이 이미 있을 때, 같은 문제를 다시 시도하면 기존 문제 기록을 반환한다.")
    @Test
    void tryExistDetailThenReturnExistDetail() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> triedProblem = getProblems(DailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, DailyDefense, member, triedProblem);

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
        DailyDefense DailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(DailyDefense, 2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, DailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getSolvedCount()).isZero();
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
        DailyDefense DailyDefense = createDailyDefense(createdDate);

        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(DailyDefense, 2L);

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 23, 59, 59);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, DailyDefense, member, problems);

        // then
        assertNotNull(dailyDefenseRecord);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 정답 여부는 모두 오답 상태여야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(DailyDefense, 2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, DailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getDetails())
                .extracting("isSolved")
                .contains(false);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 제출 횟수는 모두 0회여야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(DailyDefense, 2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, DailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getDetails())
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 정답 코드는 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(DailyDefense,2L);

        // when
        DailyRecord dailyDefenseRecord = DailyRecord.tryDefense(startTime, DailyDefense, member, problems);

        // then
        assertThat(dailyDefenseRecord.getDetails())
                .extracting("solvedCode")
                .contains((String)null);
    }
    private Map<Long, Problem> getProblems(DailyDefense DailyDefense, Long problemNumber) {
        return DailyDefense.getDailyDefenseProblems().stream()
                .filter(p -> p.getProblemNumber().equals(problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }
    private DailyDefense createDailyDefense() {
        List<Problem> problems = createProblems();
        LocalDate createdDate = LocalDate.of(2024, 3, 1);
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problems);
    }
    private DailyDefense createDailyDefense(LocalDate createdDate) {
        List<Problem> problems = createProblems();
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problems);
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