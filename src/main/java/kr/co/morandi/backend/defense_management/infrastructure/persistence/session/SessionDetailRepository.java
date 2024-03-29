package kr.co.morandi.backend.defense_management.infrastructure.persistence.session;

import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDetailRepository extends JpaRepository<SessionDetail, Long> {
}
