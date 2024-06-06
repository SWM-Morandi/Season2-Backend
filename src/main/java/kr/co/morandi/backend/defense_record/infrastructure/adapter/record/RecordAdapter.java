package kr.co.morandi.backend.defense_record.infrastructure.adapter.record;

import kr.co.morandi.backend.defense_record.application.port.out.record.RecordPort;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.record.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecordAdapter implements RecordPort {

    private final RecordRepository recordRepository;
    @Override
    public Optional<Record<? extends Detail>> findRecordById(Long recordId) {
            return recordRepository.findById(recordId);
    }

    @Override
    public Optional<Record<? extends Detail>> findRecordFetchJoinWithDetail(Long recordId) {
        return recordRepository.findRecordFetchJoinWithDetail(recordId);
    }

    @Override
    public Optional<Record<? extends Detail>> findRecordFetchJoinWithDetailAndProblem(Long recordId) {
        return recordRepository.findRecordFetchJoinWithDetailAndProblem(recordId);
    }

    @Override
    public void saveRecord(Record<?> record) {
        recordRepository.save(record);
    }
}
