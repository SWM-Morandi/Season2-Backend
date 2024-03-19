package kr.co.morandi.backend.domain.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.problem.model.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class DailyDefenseTest {
    @DisplayName("오늘의 문제 세트가 만들어진 시점에서 시도한 사람의 수는 0명 이어야 한다.")
    @Test
    void attemptCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDate now = LocalDateTime.now().toLocalDate();

        // when
        DailyDefense dailyDefense = DailyDefense.create(now, "오늘의 문제 테스트", problems);

        // then
        assertThat(dailyDefense.getAttemptCount()).isZero();
    }
    @DisplayName("오늘의 문제가 만들어진 시점에 등록된 날짜는 만들어진 시점과 같아야 한다.")
    @Test
    void testDateEqualNow() {
        // given
        List<Problem> problems = createProblems();
        LocalDate now = LocalDateTime.now().toLocalDate();

        // when
        DailyDefense dailyDefense = DailyDefense.create(now, "오늘의 문제 테스트", problems);

        // then
        assertThat(dailyDefense.getDate()).isEqualTo(now);
    }
    @DisplayName("오늘의 문제가 만들어진 이름은 일치해야한다.")
    @Test
    void contentNameIsEqual() {
        // given
        List<Problem> problems = createProblems();
        LocalDate now = LocalDateTime.now().toLocalDate();


        // when
        DailyDefense dailyDefense = DailyDefense.create(now, "오늘의 문제 테스트", problems);

        // then
        assertThat(dailyDefense.getContentName()).isEqualTo("오늘의 문제 테스트");
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return List.of(problem1, problem2, problem3);
    }
}