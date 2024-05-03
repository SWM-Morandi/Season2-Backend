package kr.co.morandi.backend.defense_information.domain.service.dailydefense;

import kr.co.morandi.backend.defense_information.application.port.out.dailydefense.DailyDefenseProblemPort;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.problem_generation_strategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;

@Component
@RequiredArgsConstructor
public class DailyDefenseProblemStrategy implements ProblemGenerationStrategy {

    private final DailyDefenseProblemPort dailyDefenseProblemPort;
    @Override
    public Map<Long, Problem> generateDefenseProblems(Defense defense) {
        final List<DailyDefenseProblem> defenseProblems = dailyDefenseProblemPort.findAllProblemsContainsDefenseId(defense.getDefenseId());
        return defenseProblems.stream()
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }
    @Override
    public DefenseType getDefenseType() {
        return DAILY;
    }
}
