package kr.co.morandi.backend.defense_information.application.outputport.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.defense.RandomCriteria;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

import java.util.Map;

public interface DailyDefenseProblemPort {

    Map<Long, Problem> getDailyDefenseProblem(Map<Long, RandomCriteria> criteria);
}
