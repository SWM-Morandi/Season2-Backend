package kr.co.morandi.backend.application.port.out.exammanagement.session;

import kr.co.morandi.backend.domain.exammanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.member.model.Member;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DefenseSessionPort {

    DefenseSession saveDefenseSession(DefenseSession defenseSession);
    Optional<DefenseSession> findTodaysDailyDefenseSession(Member member, LocalDateTime now);
}
