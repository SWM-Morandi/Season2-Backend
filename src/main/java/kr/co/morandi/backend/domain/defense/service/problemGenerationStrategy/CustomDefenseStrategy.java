package kr.co.morandi.backend.domain.defense.service.problemGenerationStrategy;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.problem.Problem;
import org.springframework.stereotype.Component;

import java.util.Map;

import static kr.co.morandi.backend.domain.defense.model.DefenseType.CUSTOM;

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
