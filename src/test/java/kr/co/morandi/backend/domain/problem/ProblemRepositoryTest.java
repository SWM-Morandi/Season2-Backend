package kr.co.morandi.backend.domain.problem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static kr.co.morandi.backend.domain.defense.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.problem.ProblemStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class ProblemRepositoryTest {

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
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