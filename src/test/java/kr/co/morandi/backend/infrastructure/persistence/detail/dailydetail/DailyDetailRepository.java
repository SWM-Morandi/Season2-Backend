package kr.co.morandi.backend.infrastructure.persistence.detail.dailydetail;

import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyDetailRepository extends JpaRepository<DailyDetail, Long> {
}
