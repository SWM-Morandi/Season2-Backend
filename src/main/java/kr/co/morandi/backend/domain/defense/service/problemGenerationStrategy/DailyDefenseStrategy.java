package kr.co.morandi.backend.domain.defense.service.problemGenerationStrategy;

import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static kr.co.morandi.backend.domain.defense.model.DefenseType.DAILY;

@Component
@RequiredArgsConstructor
public class DailyDefenseStrategy implements ProblemGenerationStrategy {

    private final DailyDefenseProblemRepository dailyDefenseProblemRepository;
    @Override
    public List<Problem> generateDefenseProblems(Defense defense) {
        return dailyDefenseProblemRepository.findAllProblemsContainsDefenseId(defense.getDefenseId());
    }
    @Override
    public DefenseType getDefenseType() {
        return DAILY;
    }
}
