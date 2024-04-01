package kr.co.morandi.backend.defense_record.application.service;

import kr.co.morandi.backend.defense_record.application.dto.DailyDefenseRankPageResponse;
import kr.co.morandi.backend.defense_record.application.dto.DailyDetailRankResponse;
import kr.co.morandi.backend.defense_record.application.dto.DailyRecordRankResponse;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import kr.co.morandi.backend.defense_record.application.port.out.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyRecordRankUseCaseImpl implements DailyRecordRankUseCase {

    private final DailyRecordPort dailyRecordPort;


    // TODO 공통 등수 로직 부분 빠짐
    @Override
    public DailyDefenseRankPageResponse getDailyRecordRank(LocalDateTime requestTime, int page, int size) {
        final List<DailyRecord> dailyRecords = dailyRecordPort.findDailyRecordRank(requestTime.toLocalDate(), page, size);

        // 등수 계산
        // TODO 동점자 처리 필요
        AtomicLong initialRank = new AtomicLong((long) page * size + 1);

        final List<DailyRecordRankResponse> dailyRecordRanks = dailyRecords.stream()
                .map(dr -> {
                    String member = dr.getMember().getNickname();
                    Long rank = initialRank.getAndIncrement();
                    List<DailyDetailRankResponse> details = DailyDetailRankResponse.of(dr.getDetails());
                    Long totalSolvedTime = dr.getDetails().stream()
                            .mapToLong(DailyDetail::getSolvedTime)
                            .sum();
                    Long solvedCount = dr.getDetails().stream()
                            .filter(DailyDetail::getIsSolved)
                            .count();

                    return DailyRecordRankResponse.of(member, rank, requestTime, totalSolvedTime, solvedCount, details);
                })
                .toList();

        return DailyDefenseRankPageResponse.of(dailyRecordRanks, page, size);
    }


}
