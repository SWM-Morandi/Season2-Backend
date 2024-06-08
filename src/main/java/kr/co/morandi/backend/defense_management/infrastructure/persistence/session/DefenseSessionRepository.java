package kr.co.morandi.backend.defense_management.infrastructure.persistence.session;

import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DefenseSessionRepository extends JpaRepository<DefenseSession, Long> {
    @Query("""
        select ds
        from DefenseSession as ds
        left join fetch ds.sessionDetails
        where ds.endDateTime > :now
        and ds.defenseType = :defenseType
        and ds.member = :member
       """)
    Optional<DefenseSession> findDailyDefenseSession(Member member, DefenseType defenseType, LocalDateTime now);

    @Query("""
        select ds
        from DefenseSession as ds
        left join fetch ds.sessionDetails d
        left join fetch d.tempCodes
        where ds.defenseSessionId = :sessionId
    """)
    Optional<DefenseSession> findDefenseSessionJoinFetchTempCode(Long sessionId);
}
