package kr.co.morandi.backend.defense_record.infrastructure.adapter.record;

import kr.co.morandi.backend.common.annotation.Adapter;
import kr.co.morandi.backend.defense_record.application.port.out.record.RecordPort;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.record.RecordRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class RecordAdapter implements RecordPort {

    private final RecordRepository recordRepository;
    @Override
    public Optional<Record<? extends Detail>> findRecordById(Long defenseRecordId) {
        return recordRepository.findById(defenseRecordId);
    }

    @Override
    public Optional<Record<? extends Detail>> findRecordFetchJoinWithDetail(Long defenseRecordId) {
        return recordRepository.findRecordFetchJoinWithDetail(defenseRecordId);
    }

    @Override
    public Optional<Record<? extends Detail>> findRecordFetchJoinWithDetailAndProblem(Long defenseRecordId) {
        return recordRepository.findRecordFetchJoinWithDetailAndProblem(defenseRecordId);
    }

    @Override
    public void saveRecord(Record<? extends Detail> defenseRecord) {
        recordRepository.save(defenseRecord);
    }
}
