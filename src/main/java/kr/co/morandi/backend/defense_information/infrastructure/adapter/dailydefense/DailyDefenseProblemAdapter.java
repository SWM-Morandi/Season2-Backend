package kr.co.morandi.backend.defense_information.infrastructure.adapter.dailydefense;

import kr.co.morandi.backend.defense_information.application.outputport.dailydefense.DailyDefenseProblemPort;
import kr.co.morandi.backend.defense_information.domain.model.defense.RandomCriteria;
import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DailyDefenseProblemAdapter implements DailyDefenseProblemPort {

    private final ProblemRepository problemRepository;

    @Override
    public Map<Long, Problem> getDailyDefenseProblem(Map<Long, RandomCriteria> criteria) {

        Pageable pageable = PageRequest.of(0, 1);

        return criteria.entrySet().stream()
                .map(entry -> {
                    final RandomCriteria randomCriteria = entry.getValue();
                    final RandomCriteria.DifficultyRange difficultyRange = randomCriteria.getDifficultyRange();
                    final ProblemTier startTier = difficultyRange.getStartDifficulty();
                    final ProblemTier endTier = difficultyRange.getEndDifficulty();

                    final List<Problem> dailyDefenseProblems =
                            problemRepository.getDailyDefenseProblems(ProblemTier.tierRangeOf(startTier, endTier),
                                                                        randomCriteria.getMinSolvedCount(),
                                                                        randomCriteria.getMaxSolvedCount(),
                                                                        pageable);

                    return Map.entry(entry.getKey(), dailyDefenseProblems.get(0));
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

