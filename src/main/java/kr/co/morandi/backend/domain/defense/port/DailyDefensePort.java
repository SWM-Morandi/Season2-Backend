package kr.co.morandi.backend.domain.defense.port;

import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;

import java.time.LocalDate;

public interface DailyDefensePort {
    DailyDefense findDailyDefense(DefenseType defenseType, LocalDate date);
}
