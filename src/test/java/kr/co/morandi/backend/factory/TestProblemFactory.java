package kr.co.morandi.backend.factory;

import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.domain.model.problem.ProblemStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestProblemFactory {

    public static Problem createProblem() {
        return Problem.builder()
                .problemStatus(ProblemStatus.ACTIVE)
                .baekjoonProblemId(1000L)
                .problemTier(ProblemTier.S5)
                .solvedCount(0L)
                .build();
    }

    static Map<Integer, ProblemTier> tierMap = Map.of(1, ProblemTier.S5,
                2, ProblemTier.G4,
                3, ProblemTier.G3,
                4, ProblemTier.G1,
                5, ProblemTier.B4);

    public static Map<Long, Problem> createProblems(int count) {
        Map<Long, Problem> problems = new HashMap();
        for (int i = 0; i < count; i++) {
            problems.put((long) (i + 1), Problem.builder()
                    .problemStatus(ProblemStatus.ACTIVE)
                    .baekjoonProblemId(1000L + i)
                    .problemTier(tierMap.get(i % 5 + 1))
                    .solvedCount(0L)
                    .build());
        }
        return problems;
    }

}
