package kr.co.morandi.backend.application.port.out.record.dailyrecord;

import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRecordPort {
    DailyRecord saveDailyRecord(DailyRecord dailyRecord);
    Optional<DailyRecord> findDailyRecord(Member member, LocalDate date);
    Optional<DailyRecord> findDailyRecord(Member member, Long recordId, LocalDate date);


}
