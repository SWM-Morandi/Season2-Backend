package kr.co.morandi.backend.domain.problem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;


import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.problem.ProblemStatus.INIT;
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

}