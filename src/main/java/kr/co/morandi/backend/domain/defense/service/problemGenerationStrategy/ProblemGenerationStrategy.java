package kr.co.morandi.backend.domain.defense.service.problemGenerationStrategy;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.problem.Problem;

import java.util.Map;

public interface ProblemGenerationStrategy {

    Map<Long, Problem> generateDefenseProblems(Defense defense);

    DefenseType getDefenseType();

   }
