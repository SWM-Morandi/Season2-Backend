package kr.co.morandi.backend.defense_management.application.service.timer;

import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class DefenseTimerService {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final SessionService sessionService;

    public void startDefenseTimer(DefenseSession defenseSession) {

        long delay = Duration.between(defenseSession.getStartDateTime(), defenseSession.getEndDateTime()).toMillis();

        scheduler.schedule(() -> {
            defenseSession.terminateDefense(sessionService);
        }, delay, TimeUnit.MILLISECONDS);

    }

}
