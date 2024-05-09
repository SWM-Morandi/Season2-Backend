package kr.co.morandi.backend.defense_management.application.service.message;

import kr.co.morandi.backend.defense_management.application.port.out.defensemessaging.DefenseMessagePort;
import kr.co.morandi.backend.defense_management.domain.event.CreateDefenseMessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class CreateDefenseCableService {

    private final DefenseMessagePort defenseMessagePort;

    /*
    * Defense가 시작되면 DefenseMessagePort를 통해 SSE 연결을 생성한다.
    * */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void createConnection(CreateDefenseMessageEvent event) {
        Long defenseSessionId = event.getDefenseSessionId();

        defenseMessagePort.createConnection(defenseSessionId);
    }
}
