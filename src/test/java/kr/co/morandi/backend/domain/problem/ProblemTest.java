package kr.co.morandi.backend.domain.problem;

import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.problem.model.ProblemStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;


import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static kr.co.morandi.backend.domain.problem.model.ProblemStatus.INIT;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ProblemTest {
    @DisplayName("문제가 처음 생성될 때, 문제의 상태는 INIT이어야 한다.")
    @Test
    void createProblem() {
        // when
        Problem problem1 = Problem.create(1L, B5, 0L);

        // then
        assertThat(problem1)
                .extracting("baekjoonProblemId", "problemTier", "problemStatus", "solvedCount")
                .containsExactly(
                        1L, B5, INIT, 0L
                );
    }

    @DisplayName("문제가 활성화되면 문제의 상태는 ACTIVE여야 한다.")
    @Test
    void activate() {
        // given
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);

        // when
        problem1.activate();

        // then
        assertThat(problem1.getProblemStatus()).isEqualTo(ProblemStatus.ACTIVE);
        assertThat(problem2.getProblemStatus()).isEqualTo(INIT);

    }

}