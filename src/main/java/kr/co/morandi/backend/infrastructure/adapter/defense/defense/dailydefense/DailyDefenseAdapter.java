package kr.co.morandi.backend.infrastructure.adapter.defense.defense.dailydefense;

import kr.co.morandi.backend.application.port.out.defense.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyDefenseAdapter implements DailyDefensePort {

    private final DailyDefenseRepository dailyDefenseRepository;

    @Override
    public DailyDefense findDailyDefense(DefenseType defenseType, LocalDate date) {
        return dailyDefenseRepository.findDailyDefenseByTypeAndDate(defenseType, date)
                .orElseThrow(() -> new IllegalArgumentException("DailyDefense가 존재하지 않습니다"));
    }

    @Override
    public DailyDefense saveDailyDefense(DailyDefense dailyDefense) {
        return dailyDefenseRepository.save(dailyDefense);
    }

}