package kr.co.morandi.backend.infrastructure.persistence.detail.dailydetail;

import kr.co.morandi.backend.domain.detail.dailydefense.model.DailyDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyDetailRepository extends JpaRepository<DailyDetail, Long> {
}
