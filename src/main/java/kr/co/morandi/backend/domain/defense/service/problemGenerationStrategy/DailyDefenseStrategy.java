package kr.co.morandi.backend.domain.defense.service.problemGenerationStrategy;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.problem.Problem;
import org.springframework.stereotype.Component;

import java.util.List;

import static kr.co.morandi.backend.domain.defense.model.DefenseType.DAILY;

@Component
public class DailyDefenseStrategy implements ProblemGenerationStrategy {
    @Override
    public List<Problem> generateDefenseProblems(Defense defense) {
        return null;
    }

    @Override
    public DefenseType getDefenseType() {
        return DAILY;
    }
}
