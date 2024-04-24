package kr.co.morandi.backend.defense_record.application.port.out.record;

import kr.co.morandi.backend.defense_record.domain.model.record.Record;

import java.util.Optional;

public interface RecordPort {
    Optional<Record<?>> findRecordById(Long recordId);
    void saveRecord(Record<?> record);
}
