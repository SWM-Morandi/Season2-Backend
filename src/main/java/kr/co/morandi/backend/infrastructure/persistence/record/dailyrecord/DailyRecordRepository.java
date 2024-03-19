package kr.co.morandi.backend.infrastructure.persistence.record.dailyrecord;

import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

    @Query("""
            select dr
            from DailyRecord dr
            left join fetch dr.details d
            left join fetch d.problem
            where dr.member = :member
            and CAST(dr.testDate as localdate) = :date
     """)
    Optional<DailyRecord> findDailyRecordByMemberAndDate(Member member, LocalDate date);
}
