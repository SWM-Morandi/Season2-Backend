package kr.co.morandi.backend.defense_information.infrastructure.adapter.dailydefense;

import kr.co.morandi.backend.defense_information.application.port.out.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
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