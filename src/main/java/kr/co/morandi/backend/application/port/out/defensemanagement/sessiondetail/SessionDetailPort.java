package kr.co.morandi.backend.application.port.out.defensemanagement.sessiondetail;

import kr.co.morandi.backend.domain.defensemanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.defensemanagement.sessiondetail.model.SessionDetail;

import java.util.Optional;

public interface SessionDetailPort {
    Optional<SessionDetail> findSessionDetail(DefenseSession defenseSession, Long problemId);
}
