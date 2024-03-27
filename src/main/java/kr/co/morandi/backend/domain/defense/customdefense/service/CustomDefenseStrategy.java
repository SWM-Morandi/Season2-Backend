package kr.co.morandi.backend.domain.defense.customdefense.service;

import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.problemgenerationstrategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.domain.problem.model.Problem;
import org.springframework.stereotype.Component;

import java.util.Map;

import static kr.co.morandi.backend.domain.defense.DefenseType.CUSTOM;

@Component
public class CustomDefenseStrategy implements ProblemGenerationStrategy {

    @Override
    public Map<Long, Problem> generateDefenseProblems(Defense defense) {
        return null;
    }
    @Override
    public DefenseType getDefenseType() {
        return CUSTOM;
    }
}
