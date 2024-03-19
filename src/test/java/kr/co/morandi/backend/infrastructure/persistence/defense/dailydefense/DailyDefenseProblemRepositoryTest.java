package kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefenseProblem;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@ActiveProfiles("test")
class DailyDefenseProblemRepositoryTest {

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;
    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        dailyDefenseProblemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @DisplayName("defense 타입으로 DailyDefenseProblem을 가져올 수 있다.")
    @Test
    void findAllProblemsContainsDefenseId() {
        // given
        LocalDate defenseDate = LocalDate.of(2021, 1, 1);
        Defense defense = createDailyDefense(defenseDate);

        // when
        List<DailyDefenseProblem> dailyDefenseProblems = dailyDefenseProblemRepository.findAllProblemsContainsDefenseId(defense.getDefenseId());

        // then
        assertThat(dailyDefenseProblems).hasSize(3)
                .extracting("problem.baekjoonProblemId", "problem.problemTier", "problem.solvedCount")
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