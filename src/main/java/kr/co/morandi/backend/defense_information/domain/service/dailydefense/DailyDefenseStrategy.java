package kr.co.morandi.backend.defense_information.domain.service.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.problem_generation_strategy.ProblemGenerationStrategy;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;

@Component
@RequiredArgsConstructor
public class DailyDefenseStrategy implements ProblemGenerationStrategy {

    //TODO 여기도 Port로 바꿔야함
    private final DailyDefenseProblemRepository dailyDefenseProblemRepository;
    @Override
    public Map<Long, Problem> generateDefenseProblems(Defense defense) {
        final List<DailyDefenseProblem> defenseProblems = dailyDefenseProblemRepository.findAllProblemsContainsDefenseId(defense.getDefenseId());
        return defenseProblems.stream()
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }
    @Override
    public DefenseType getDefenseType() {
        return DAILY;
    }
}
