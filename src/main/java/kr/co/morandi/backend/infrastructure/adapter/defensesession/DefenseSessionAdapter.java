package kr.co.morandi.backend.infrastructure.adapter.defensesession;

import kr.co.morandi.backend.application.port.out.defensemanagement.defensesession.DefenseSessionPort;
import kr.co.morandi.backend.domain.defensemanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.infrastructure.persistence.defensemanagement.session.DefenseSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.co.morandi.backend.domain.defense.DefenseType.DAILY;

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
