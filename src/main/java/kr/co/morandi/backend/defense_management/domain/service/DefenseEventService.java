package kr.co.morandi.backend.defense_management.domain.service;

import kr.co.morandi.backend.defense_management.application.service.timer.DefenseTimerService;
import kr.co.morandi.backend.defense_management.domain.event.DefenseStartTimerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class DefenseEventService {

    private final DefenseTimerService defenseTimerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDefenseStartTimerEvent(DefenseStartTimerEvent event) {
        defenseTimerService.startDefenseTimer(event.getSessionId(), event.getStartDateTime(), event.getEndDateTime());
    }
}