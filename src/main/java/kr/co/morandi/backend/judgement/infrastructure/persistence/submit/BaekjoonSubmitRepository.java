package kr.co.morandi.backend.judgement.infrastructure.persistence.submit;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaekjoonSubmitRepository extends JpaRepository<BaekjoonSubmit, Long> {

    @Query("""
            SELECT s
            FROM BaekjoonSubmit s
            JOIN FETCH s.detail d
            JOIN FETCH d.record
            WHERE s.submitId = :submitId
    """)
    Optional<BaekjoonSubmit> findSubmitJoinFetchDetailAndRecord(Long submitId);
}
