package kr.co.morandi.backend.factory;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

import java.time.LocalDate;
import java.util.Map;

public class TestDefenseFactory {

    public static DailyDefense createDailyDefense(Map<Long, Problem> problems) {

        return DailyDefense.builder()
                .problems(problems)
                .date(LocalDate.of(2021, 1, 1))
                .contentName("contentName")
                .build();
    }
}
