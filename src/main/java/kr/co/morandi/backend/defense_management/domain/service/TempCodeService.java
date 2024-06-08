package kr.co.morandi.backend.defense_management.domain.service;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_management.domain.error.SessionErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.judgement.domain.event.TempCodeSaveEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TempCodeService {

    private final DefenseSessionPort defenseSessionPort;

    @EventListener
    @Transactional
    @Async("baekjoonJudgementExecutor")
    public void saveTempCode(final TempCodeSaveEvent tempCodeSaveEvent) {
        final Long defenseSessionId = tempCodeSaveEvent.getDefenseSessionId();

        final DefenseSession defenseSession = defenseSessionPort.findDefenseSessionJoinFetchTempCode(defenseSessionId)
                .orElseThrow(() -> new MorandiException(SessionErrorCode.SESSION_NOT_FOUND));

        defenseSession.updateTempCode(tempCodeSaveEvent.getProblemNumber(),
                tempCodeSaveEvent.getLanguage(),
                tempCodeSaveEvent.getSourceCode());

        defenseSessionPort.saveDefenseSession(defenseSession);
    }
}
