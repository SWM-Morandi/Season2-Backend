package kr.co.morandi.backend.application.usecase;

import kr.co.morandi.backend.domain.defense.port.DailyDefensePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefenseUseCase {
    private final DailyDefensePort dailyDefenseAdapter;
}
