package kr.co.morandi.backend.defense_management.application.service.message;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.application.port.out.defensemessaging.DefenseMessagePort;
import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_management.domain.error.SessionErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class DefenseMessageService {

    private final DefenseMessagePort defenseMessagePort;
    private final DefenseSessionPort defenseSessionPort;

    public SseEmitter getConnection(Long defenseSessionId, Long memberId) {
        DefenseSession defenseSession = defenseSessionPort.findDefenseSessionById(defenseSessionId)
                .orElseThrow(() -> new MorandiException(SessionErrorCode.SESSION_NOT_FOUND));

        /*
         *  세션의 소유자인지 확인
         *  세션의 소유자가 아닐 경우 예외 발생
         * */
        defenseSession.validateSessionOwner(memberId);
        if(defenseSession.isTerminated()) {
            throw new MorandiException(SessionErrorCode.SESSION_TERMINATED);
        }
        return defenseMessagePort.getConnection(defenseSessionId);
    }

}
