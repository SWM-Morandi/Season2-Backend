package kr.co.morandi.backend.domain.defense.problemGenerationStrategy;

import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.problem.model.Problem;

import java.util.Map;

public interface ProblemGenerationStrategy {

    Map<Long, Problem> generateDefenseProblems(Defense defense);
    DefenseType getDefenseType();

}
