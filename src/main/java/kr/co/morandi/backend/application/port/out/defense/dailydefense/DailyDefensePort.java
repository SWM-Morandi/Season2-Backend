package kr.co.morandi.backend.application.port.out.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;

import java.time.LocalDate;

public interface DailyDefensePort {
    DailyDefense findDailyDefense(DefenseType defenseType, LocalDate date);
}
