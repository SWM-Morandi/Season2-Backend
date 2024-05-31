package kr.co.morandi.backend.defense_record.application.port.out.record;

import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;

import java.util.Optional;

public interface RecordPort {
    Optional<Record<? extends Detail>> findRecordById(Long recordId);
    Optional<Record<? extends Detail>> findRecordByIdFetchDetails(Long recordId);
    void saveRecord(Record<? extends Detail> record);
}
