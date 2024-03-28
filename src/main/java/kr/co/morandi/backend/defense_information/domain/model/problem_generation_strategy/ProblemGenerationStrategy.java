package kr.co.morandi.backend.defense_information.domain.model.problem_generation_strategy;

import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

import java.util.Map;

public interface ProblemGenerationStrategy {

    Map<Long, Problem> generateDefenseProblems(Defense defense);
    DefenseType getDefenseType();

}
