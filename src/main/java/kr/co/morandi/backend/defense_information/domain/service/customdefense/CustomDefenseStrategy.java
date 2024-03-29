package kr.co.morandi.backend.defense_information.domain.service.customdefense;

import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.problem_generation_strategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.springframework.stereotype.Component;

import java.util.Map;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.CUSTOM;

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
