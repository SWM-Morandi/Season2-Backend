package kr.co.morandi.backend.defense_information.infrastructure.scheduler.dailydefense;

import kr.co.morandi.backend.defense_information.domain.service.dailydefense.DailyDefenseGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyDefenseGenerationScheduler {

    private final DailyDefenseGenerationService dailyDefenseGenerationService;

    @Scheduled(cron = "0 0 23 * * ?", zone = "Asia/Seoul")
    public void generateDailyDefense() {
        LocalDateTime now = LocalDateTime.now();
        boolean result = dailyDefenseGenerationService.generateDailyDefense(now);
        if (!result) {
            throw new RuntimeException("Daily defense generation failed");
        }

        log.info("Daily defense generation success");
    }

}
