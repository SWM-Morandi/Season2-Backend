package kr.co.morandi.backend.domain.defense.service;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.defense.service.problemGenerationStrategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.domain.problem.Problem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProblemGenerationService {
    private final Map<DefenseType, ProblemGenerationStrategy> strategies;
    public ProblemGenerationService(List<ProblemGenerationStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(ProblemGenerationStrategy::getDefenseType, strategy -> strategy));
    }
    public List<Problem> getDefenseProblems(Defense defense) {
        return strategies.get(defense.getType()).generateDefenseProblems(defense);
    }

}
