package kr.co.morandi.backend.domain.defense.service.problemGenerationStrategy;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defense.model.DefenseType.DAILY;

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
