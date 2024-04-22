package kr.co.morandi.backend.defense_record.application.port.out.dailyrecord;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyRecordPort {

    DailyRecord saveDailyRecord(DailyRecord dailyRecord);
    Optional<DailyRecord> findDailyRecord(Member member, LocalDate date);
    Optional<DailyRecord> findDailyRecord(Member member, Long recordId, LocalDate date);
    Page<DailyRecord> findDailyRecordRank(LocalDate requestDate, Integer page, Integer size);
}
