package kr.co.morandi.backend.defense_management.infrastructure.adapter.session;

import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.DefenseSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;

@Service
@RequiredArgsConstructor
public class DefenseSessionAdapter implements DefenseSessionPort {

    private final DefenseSessionRepository defenseSessionRepository;

    @Override
    public DefenseSession saveDefenseSession(DefenseSession defenseSession) {
        return defenseSessionRepository.save(defenseSession);
    }

    @Override
    public Optional<DefenseSession> findTodaysDailyDefenseSession(Member member, LocalDateTime now) {
        return defenseSessionRepository.findDailyDefenseSession(member, DAILY, now);
    }
}
