package kr.co.morandi.backend.domain.defense.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.service.defense.ProblemGenerationService;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class DailyDefenseTest {

    @DisplayName("오늘의 문제세트에 포함된 문제를 가져올 수 있다.")
    @Test
    void getTryingProblem() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        final DailyDefense dailyDefense = DailyDefense.create(now.toLocalDate(), "오늘의 문제 테스트", problemMap);

        Map<Long, Problem> expectedProblems = Map.of(
                1L, problemMap.get(1L),
                2L, problemMap.get(2L),
                3L, problemMap.get(3L)
        );
        final ProblemGenerationService problemGenerationService = mock(ProblemGenerationService.class);

        when(problemGenerationService.getDefenseProblems(dailyDefense)).thenReturn(expectedProblems);

        // when
        final Map<Long, Problem> tryingProblem = dailyDefense.getTryingProblem(1L, problemGenerationService);

        // then
        assertThat(tryingProblem.entrySet()).isNotEmpty()
                .extracting(Map.Entry::getKey, Map.Entry::getValue)
                .containsExactlyInAnyOrder(
                        tuple(1L, problemMap.get(1L))
                );

    }
    @DisplayName("오늘의 문제를 응시할 때 끝나는 시간은 오늘의 문제 날짜 직전까지이다.")
    @Test
    void getEndTime() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        // when
        DailyDefense dailyDefense = DailyDefense.create(now.toLocalDate(), "오늘의 문제 테스트", problemMap);

        // then
        assertThat(dailyDefense.getEndTime(now))
                .isEqualTo(now.toLocalDate().atTime(23, 59, 59));
    }
    @DisplayName("오늘의 문제 세트가 만들어진 시점에서 시도한 사람의 수는 0명 이어야 한다.")
    @Test
    void attemptCountIsZero() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        // when
        DailyDefense dailyDefense = DailyDefense.create(now.toLocalDate(), "오늘의 문제 테스트", problemMap);

        // then
        assertThat(dailyDefense.getAttemptCount()).isZero();
    }
    @DisplayName("오늘의 문제가 만들어진 시점에 등록된 날짜는 만들어진 시점과 같아야 한다.")
    @Test
    void testDateEqualNow() {
        // given
        LocalDate now = LocalDate.of(2021, 1, 1);

        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        // when
        DailyDefense dailyDefense = DailyDefense.create(now, "오늘의 문제 테스트", problemMap);

        // then
        assertThat(dailyDefense.getDate()).isEqualTo(now);
    }
    @DisplayName("오늘의 문제가 만들어진 이름은 일치해야한다.")
    @Test
    void contentNameIsEqual() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        // when
        DailyDefense dailyDefense = DailyDefense.create(now.toLocalDate(), "오늘의 문제 테스트", problemMap);

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