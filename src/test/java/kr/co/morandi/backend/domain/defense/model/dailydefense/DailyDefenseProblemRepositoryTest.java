package kr.co.morandi.backend.domain.defense.model.dailydefense;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.model.tier.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DailyDefenseProblemRepositoryTest {

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;
    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @DisplayName("defense 타입으로 DailyDefenseProblem을 가져올 수 있다.")
    @Test
    void findAllProblemsContainsDefenseId() {
        // given
        LocalDate defenseDate = LocalDate.of(2021, 1, 1);
        Defense defense = createDailyDefense(defenseDate);

        // when
        List<Problem> dailyDefenseProblems = dailyDefenseProblemRepository.findAllProblemsContainsDefenseId(defense.getDefenseId());

        // then
        assertThat(dailyDefenseProblems).hasSize(3)
                .extracting("baekjoonProblemId", "problemTier", "solvedCount")
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