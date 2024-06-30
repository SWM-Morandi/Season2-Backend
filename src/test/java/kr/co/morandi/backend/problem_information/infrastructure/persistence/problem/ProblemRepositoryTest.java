package kr.co.morandi.backend.problem_information.infrastructure.persistence.problem;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.problem_information.domain.model.problem.ProblemStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class ProblemRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ProblemRepository problemRepository;

    @DisplayName("startTier와 endTier사이고, ACTIVE, dailyDefenseProblem에 속하지 않은 문제들을 가져올 수 있다.")
    @Test
    void findDailyDefenseProblems() {
        // given
        Problem problem1 = Problem.create(1L, B5, 1000L);
        Problem problem2 = Problem.create(2L, S5, 2000L);
        problem2.activate();
        Problem problem3 = Problem.create(3L, G5, 3000L);

        problemRepository.saveAll(List.of(problem1, problem2, problem3));

        List<ProblemTier> tierRange = ProblemTier.tierRangeOf(S5, S1);
        Long startSolvedCount = 1500L;
        Long endSolvedCount = 2500L;

        PageRequest pageRequest = PageRequest.of(0, 1);

        List<Problem> problems = problemRepository.getDailyDefenseProblems(tierRange, startSolvedCount, endSolvedCount, pageRequest);


        // then
        assertThat(problems).hasSize(1)
                .allMatch(problem -> problem.getProblemTier().compareTo(S5) >= 0
                        && problem.getProblemTier().compareTo(S1) <= 0);

    }

    @DisplayName("활성화된 문제들의 리스트를 조회할 수 있다.")
    @Test
    void findAllByProblemStatus() {
        // given
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        problem1.activate();
        problem2.activate();
        problemRepository.saveAll(List.of(problem1, problem2, problem3));

        // when
        List<Problem> problems = problemRepository.findAllByProblemStatus(ACTIVE);

        // then
        assertThat(problems)
                .hasSize(2)
                .extracting("baekjoonProblemId", "problemTier", "problemStatus", "solvedCount")
                .containsExactlyInAnyOrder(
                        tuple(1L, B5, ACTIVE, 0L),
                        tuple(2L, S5, ACTIVE, 0L)
                );
    }

}