package kr.co.morandi.backend.defense_information.application.port.out.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.domain.model.defense.RandomCriteria;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

import java.util.List;
import java.util.Map;

public interface DailyDefenseProblemPort {

    Map<Long, Problem> getDailyDefenseProblem(Map<Long, RandomCriteria> criteria);

    List<DailyDefenseProblem> findAllProblemsContainsDefenseId(Long defenseId);
}
