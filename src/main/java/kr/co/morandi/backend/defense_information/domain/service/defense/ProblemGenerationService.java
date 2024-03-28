package kr.co.morandi.backend.defense_information.domain.service.defense;

import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.problem_generation_strategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
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
