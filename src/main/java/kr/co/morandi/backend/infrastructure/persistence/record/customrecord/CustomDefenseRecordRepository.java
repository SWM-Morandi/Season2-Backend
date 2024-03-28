package kr.co.morandi.backend.infrastructure.persistence.record.customrecord;

import kr.co.morandi.backend.domain.record.customdefenserecord.model.CustomRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomDefenseRecordRepository extends JpaRepository<CustomRecord, Long> {
}
