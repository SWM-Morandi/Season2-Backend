package kr.co.morandi.backend.domain.defense.problemGenerationStrategy.service;

import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.problemGenerationStrategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.domain.problem.model.Problem;
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
    public Map<Long, Problem> getDefenseProblems(Defense defense) {
        return strategies.get(defense.getType()).generateDefenseProblems(defense);
    }

}
