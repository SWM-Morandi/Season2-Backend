package kr.co.morandi.backend.domain.defense;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefenseUseCase {
    private final DailyDefensePort dailyDefenseAdapter;
}
