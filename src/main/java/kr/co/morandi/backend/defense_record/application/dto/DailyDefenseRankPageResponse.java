package kr.co.morandi.backend.defense_record.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyDefenseRankPageResponse {

    private List<DailyRecordRankResponse> dailyRecords;
    private Integer totalPage;
    private Integer currentPage;

    public static DailyDefenseRankPageResponse of(List<DailyRecordRankResponse> dailyRecords, Integer totalPage, Integer currentPage) {
        return DailyDefenseRankPageResponse.builder()
                .dailyRecords(dailyRecords)
                .totalPage(totalPage)
                .currentPage(currentPage)
                .build();
    }
    @Builder
    private DailyDefenseRankPageResponse(List<DailyRecordRankResponse> dailyRecords, Integer totalPage, Integer currentPage) {
        this.dailyRecords = dailyRecords;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }
}
