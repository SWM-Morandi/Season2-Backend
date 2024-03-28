package kr.co.morandi.backend.application.port.out.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.problem.model.Problem;

import java.util.Map;

public interface DailyDefenseProblemPort {

    Map<Long, Problem> getDailyDefenseProblem(Map<Long, RandomCriteria> criteria);
}
