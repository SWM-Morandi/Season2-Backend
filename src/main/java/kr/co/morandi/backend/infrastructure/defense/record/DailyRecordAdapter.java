package kr.co.morandi.backend.infrastructure.defense.record;

import kr.co.morandi.backend.domain.defense.port.record.DailyRecordPort;
import kr.co.morandi.backend.domain.record.dailydefense.DailyRecord;
import kr.co.morandi.backend.domain.record.dailydefense.DailyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyRecordAdapter implements DailyRecordPort {
    private final DailyRecordRepository dailyRecordRepository;
    @Override
    public DailyRecord saveDailyRecord(DailyRecord dailyRecord) {
        return dailyRecordRepository.save(dailyRecord);
    }
}
