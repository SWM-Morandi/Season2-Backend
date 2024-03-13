package kr.co.morandi.backend.domain.defense.service;

import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static kr.co.morandi.backend.domain.defense.model.tier.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@ActiveProfiles("test")
class ProblemGenerationServiceTest {

    @Autowired
    private ProblemGenerationService problemGenerationService;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        dailyDefenseProblemRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
    }

    @DisplayName("DailyDefense의 Problem목록을 가져올 수 있다.")
    @Test
    void getDefenseProblems() {
        // given
        LocalDate defenseDate = LocalDate.of(2021, 1, 1);
        DailyDefense dailyDefense = createDailyDefense(defenseDate);

        // when
        Map<Long, Problem> defenseProblems = problemGenerationService.getDefenseProblems(dailyDefense);

        // then
        assertThat(defenseProblems.values()).hasSize(3)
                .extracting(Problem::getBaekjoonProblemId, Problem::getProblemTier, Problem::getSolvedCount)
                .containsExactlyInAnyOrder(
                        tuple(1L, B5, 0L),
                        tuple(2L, S5, 0L),
                        tuple(3L, G5, 0L)
                );

    }

    private DailyDefense createDailyDefense(LocalDate date) {
        List<Problem> problems = createProblems();
        return dailyDefenseRepository.save(DailyDefense.create(date, "오늘의 문제 테스트", problems));
    }

    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }

}