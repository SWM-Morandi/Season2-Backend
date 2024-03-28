package kr.co.morandi.backend.defense_information.application.outputport.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;

import java.time.LocalDate;

public interface DailyDefensePort {
    DailyDefense findDailyDefense(DefenseType defenseType, LocalDate date);

    DailyDefense saveDailyDefense(DailyDefense dailyDefense);
}
