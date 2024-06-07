package kr.co.morandi.backend.defense_record.infrastructure.persistence.record;

import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record<? extends Detail>, Long> {

    @Query("""
        SELECT r
        FROM Record r
        LEFT JOIN FETCH r.details d
        WHERE r.recordId = :recordId
    """)
    Optional<Record<? extends Detail>> findRecordFetchJoinWithDetail(Long recordId);

    @Query("""
        SELECT r
        FROM Record r
        LEFT JOIN FETCH r.details d
        LEFT JOIN FETCH d.problem
        WHERE r.recordId = :recordId
    """)
    Optional<Record<? extends Detail>> findRecordFetchJoinWithDetailAndProblem(Long recordId);
}
