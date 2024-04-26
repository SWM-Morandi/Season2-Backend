    package kr.co.morandi.backend.defense_management.application.service.timer;

    import kr.co.morandi.backend.defense_management.domain.service.SessionService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.time.Duration;
    import java.time.LocalDateTime;
    import java.util.concurrent.Executors;
    import java.util.concurrent.ScheduledExecutorService;
    import java.util.concurrent.TimeUnit;

    @Service
    @RequiredArgsConstructor
    public class DefenseTimerService {

        private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        private final SessionService sessionService;

        public void startDefenseTimer(Long defenseSessionId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

            long delay = Duration.between(startDateTime, endDateTime).toMillis();

            scheduler.schedule(() -> {
                sessionService.terminateDefense(defenseSessionId);
            }, delay, TimeUnit.MILLISECONDS);

        }

    }
