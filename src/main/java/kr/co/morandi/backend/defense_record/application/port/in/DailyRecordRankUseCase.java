package kr.co.morandi.backend.defense_record.application.port.in;

import kr.co.morandi.backend.defense_record.application.dto.DailyDefenseRankPageResponse;

import java.time.LocalDateTime;

public interface DailyRecordRankUseCase {

    // TODO 공통 등수 로직 부분 빠짐
    DailyDefenseRankPageResponse getDailyRecordRank(LocalDateTime requestTime, int page, int size);
}
