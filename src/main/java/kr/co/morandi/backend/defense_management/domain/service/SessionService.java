package kr.co.morandi.backend.defense_management.domain.service;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_management.domain.error.SessionErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_record.application.port.out.record.RecordPort;
import kr.co.morandi.backend.defense_record.domain.error.RecordErrorCode;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final DefenseSessionPort defenseSessionPort;
    private final RecordPort recordPort;

    @Transactional
    public void terminateDefense(Long sessionId) {
        final DefenseSession defenseSession = defenseSessionPort.findDefenseSessionById(sessionId)
                .orElseThrow(() -> new MorandiException(SessionErrorCode.SESSION_NOT_FOUND));

        defenseSession.terminateSession();

        final Record<?> record = recordPort.findRecordById(defenseSession.getRecordId())
                .orElseThrow(() -> new MorandiException(RecordErrorCode.RECORD_NOT_FOUND));

        record.terminteDefense();

        defenseSessionPort.saveDefenseSession(defenseSession);
        recordPort.saveRecord(record);
    }
}
