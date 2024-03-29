package kr.co.morandi.backend.defense_information.domain.model.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class DailyDefenseProblemTest {
    @DisplayName("오늘의 문제가 만들어졌을 때, 초기의 문제 제출횟수는 0이어야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        // when
        DailyDefense dailyDefense = DailyDefense.create(now.toLocalDate(), "오늘의 문제 테스트", problemMap);

        // then
        assertThat(dailyDefense.getDailyDefenseProblems())
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L, 0L, 0L);
    }

    @DisplayName("오늘의 문제가 만들어졌을 때, 초기 정답자 수는 0이어야 한다.")
    @Test
    void solvedCountIsZero() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        // when
        DailyDefense dailyDefense = DailyDefense.create(now.toLocalDate(), "오늘의 문제 테스트", problemMap);

        // then
        assertThat(dailyDefense.getDailyDefenseProblems())
                .extracting("solvedCount")
                .containsExactlyInAnyOrder(0L, 0L, 0L);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return List.of(problem1, problem2, problem3);
    }
}