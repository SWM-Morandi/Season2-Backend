package kr.co.morandi.backend.infrastructure.persistence.defensemanagement.session;

import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.exammanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.member.model.Member;
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
}
