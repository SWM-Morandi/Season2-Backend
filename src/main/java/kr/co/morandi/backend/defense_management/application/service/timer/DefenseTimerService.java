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
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> defenseSessionTimer = new ConcurrentHashMap<>();
    private final SessionService sessionService;

    public void startDefenseTimer(DefenseSession defenseSession) {
        if(isAlreadySettedTimer(defenseSession)) {
            return;
        }
        long delay = Duration.between(defenseSession.getStartDateTime(), defenseSession.getEndDateTime()).toMillis();

        final ScheduledFuture<?> schedule = scheduler.schedule(() -> {
            sessionService.terminateDefense(defenseSession.getDefenseSessionId());
            defenseSessionTimer.remove(defenseSession.getDefenseSessionId());
        }, delay, TimeUnit.MILLISECONDS);

        defenseSessionTimer.put(defenseSession.getDefenseSessionId(), schedule);
    }

    /*
    * 현재는 단일 인스턴스이므로 ConcurrentHashMap을 사용하여 중복 타이머가 설정되지 않도록 하였습니다.
    *
    * scale-out될 경우에는 분산 환경에서 중복 타이머가 설정되지 않도록 하기 위해 Redis와 같은 분산 캐시를 사용하여 중복 타이머가 설정되지 않도록 해야합니다.
    * */
    private boolean isAlreadySettedTimer(DefenseSession defenseSession) {
        return defenseSessionTimer.containsKey(defenseSession.getDefenseSessionId());
    }

}
