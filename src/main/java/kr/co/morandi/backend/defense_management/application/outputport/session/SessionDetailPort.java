package kr.co.morandi.backend.defense_management.application.outputport.session;

import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;

import java.util.Optional;

public interface SessionDetailPort {
    Optional<SessionDetail> findSessionDetail(DefenseSession defenseSession, Long problemId);
}
