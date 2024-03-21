package kr.co.morandi.backend.infrastructure.persistence.defensemanagement.sessiondetail;

import kr.co.morandi.backend.domain.exammanagement.sessiondetail.model.SessionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDetailRepository extends JpaRepository<SessionDetail, Long> {
}
