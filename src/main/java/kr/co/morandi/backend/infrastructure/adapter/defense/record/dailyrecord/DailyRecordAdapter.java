package kr.co.morandi.backend.infrastructure.adapter.defense.record.dailyrecord;

import kr.co.morandi.backend.application.port.out.record.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
import kr.co.morandi.backend.infrastructure.persistence.record.dailyrecord.DailyRecordRepository;
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
