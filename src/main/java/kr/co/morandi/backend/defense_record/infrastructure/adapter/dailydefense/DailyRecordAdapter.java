package kr.co.morandi.backend.defense_record.infrastructure.adapter.dailydefense;

import kr.co.morandi.backend.defense_record.application.outputport.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DailyRecordAdapter implements DailyRecordPort {

    private final DailyRecordRepository dailyRecordRepository;

    @Override
    public DailyRecord saveDailyRecord(DailyRecord dailyRecord) {
        return dailyRecordRepository.save(dailyRecord);
    }
    @Override
    public Optional<DailyRecord> findDailyRecord(Member member, LocalDate date) {
        return dailyRecordRepository.findDailyRecordByMemberAndDate(member, date);

    }
    @Override
    public Optional<DailyRecord> findDailyRecord(Member member, Long recordId, LocalDate date) {
        return dailyRecordRepository.findDailyRecordByRecordId(member, recordId, date);
    }
}
