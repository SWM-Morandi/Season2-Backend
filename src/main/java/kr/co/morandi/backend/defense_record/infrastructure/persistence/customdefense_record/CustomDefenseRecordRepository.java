package kr.co.morandi.backend.defense_record.infrastructure.persistence.customdefense_record;

import kr.co.morandi.backend.defense_record.domain.model.customdefense_record.CustomRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomDefenseRecordRepository extends JpaRepository<CustomRecord, Long> {
}
