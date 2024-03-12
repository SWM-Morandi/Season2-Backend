package kr.co.morandi.backend.domain.defense.port.record;

import kr.co.morandi.backend.domain.record.dailydefense.DailyRecord;

public interface DailyRecordPort {
    DailyRecord saveDailyRecord(DailyRecord dailyRecord);
}
