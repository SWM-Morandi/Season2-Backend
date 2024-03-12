package kr.co.morandi.backend.domain.record.dailydefense;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

}
