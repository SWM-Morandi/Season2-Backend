package kr.co.morandi.backend.infrastructure.defense;

import kr.co.morandi.backend.domain.defense.port.DailyDefensePort;
import kr.co.morandi.backend.domain.defense.model.DefenseType;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyDefenseAdapter implements DailyDefensePort {

    private final DailyDefenseRepository dailyDefenseRepository;

    @Override
    public DailyDefense findDailyDefense(DefenseType defenseType, LocalDate date) {
        return dailyDefenseRepository.findDailyDefenseByDefenseTypeAndDate(defenseType, date)
                .orElseThrow(() -> new IllegalArgumentException("DailyDefense가 존재하지 않습니다"));
    }


}