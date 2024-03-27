package kr.co.morandi.backend.infrastructure.adapter.defense.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseRepository;
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

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@ActiveProfiles("test")
class DailyDefenseProblemAdapterTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseProblemAdapter dailyDefenseProblemAdapter;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("오늘의 문제로 출제된 적이 있는 문제는 출제되지 않는다.")
    @Test
    void getDailyDefenseProblemWithAlreadySolved() {
        // given
        createProblems();
        RandomCriteria randomCriteria1 = RandomCriteria.builder()
                .minSolvedCount(500L)
                .maxSolvedCount(1500L)
                .difficultyRange(RandomCriteria.DifficultyRange.of(B5, B1))
                .build();
        RandomCriteria randomCriteria2 = RandomCriteria.builder()
                .minSolvedCount(1500L)
                .maxSolvedCount(2500L)
                .difficultyRange(RandomCriteria.DifficultyRange.of(S5, S1))
                .build();

        // when

        // then

    }
    @DisplayName("오늘의 문제에 포함되는 문제들을 의도하는 조건대로 출제할 수 있다.")
    @Test
    void getDailyDefenseProblem() {
        // given
        createProblems();
        RandomCriteria randomCriteria1 = RandomCriteria.builder()
                .minSolvedCount(500L)
                .maxSolvedCount(1500L)
                .difficultyRange(RandomCriteria.DifficultyRange.of(B5, B1))
                .build();
        RandomCriteria randomCriteria2 = RandomCriteria.builder()
                .minSolvedCount(1500L)
                .maxSolvedCount(3500L)
                .difficultyRange(RandomCriteria.DifficultyRange.of(S5, G1))
                .build();
        Map<Long, RandomCriteria> request = Map.of(1L, randomCriteria2, 2L, randomCriteria1);
        final Map<Long, Problem> dailyDefenseProblem = dailyDefenseProblemAdapter.getDailyDefenseProblem(request);

        LocalDate yesterday = LocalDate.of(2021, 1, 1);
        DailyDefense.create(yesterday, "어제 오늘의 문제", dailyDefenseProblem);

        Map<Long, RandomCriteria> newRequest = Map.of(1L, randomCriteria2);


        // when
        final Map<Long, Problem> dailyDefenseProblem2 = dailyDefenseProblemAdapter.getDailyDefenseProblem(newRequest);

        // then
        assertThat(dailyDefenseProblem2).hasSize(1);

        // 같은 범위에 2개의 문제가 있는데, 출제
        assertThat(dailyDefenseProblem.get(1L).getProblemTier()).isNotEqualTo(dailyDefenseProblem2.get(1L).getProblemTier());
    }

    private void createProblems() {
        Problem problem1 = Problem.create(1L, B5, 1000L);
        problem1.activate();

        Problem problem2 = Problem.create(2L, S5, 2000L);
        problem2.activate();

        Problem problem3 = Problem.create(3L, G5, 3000L);
        problem3.activate();

        problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }

}