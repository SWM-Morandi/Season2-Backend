package kr.co.morandi.backend.defense_management.application.port.out.session;

import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.member_management.domain.model.member.Member;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DefenseSessionPort {

    DefenseSession saveDefenseSession(DefenseSession defenseSession);
    Optional<DefenseSession> findTodaysDailyDefenseSession(Member member, LocalDateTime now);
    Optional<DefenseSession> findDefenseSessionById(Long sessionId);
    Optional<DefenseSession> findDefenseSessionJoinFetchTempCode(Long sessionId);
}
