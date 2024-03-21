package kr.co.morandi.backend.application.port.out.defensemanagement.sessiondetail;

import kr.co.morandi.backend.domain.exammanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.exammanagement.sessiondetail.model.SessionDetail;

import java.util.Optional;

public interface SessionDetailPort {
    Optional<SessionDetail> findSessionDetail(DefenseSession defenseSession, Long problemId);
}
