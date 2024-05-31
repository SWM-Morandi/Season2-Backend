package kr.co.morandi.backend.defense_record.infrastructure.persistence.record;

import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record<?>, Long> {

    @Query("""
        SELECT r
        FROM Record r
        LEFT JOIN FETCH r.details
        WHERE r.recordId = :recordId
    """)
    Optional<Record<? extends Detail>> findByIdFetchDetails(Long recordId);
}
