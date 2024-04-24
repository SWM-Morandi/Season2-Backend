package kr.co.morandi.backend.defense_record.infrastructure.persistence.record;

import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record<?>, Long> {
}
