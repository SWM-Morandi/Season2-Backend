package kr.co.morandi.backend.defense_record.application.dto;

import kr.co.morandi.backend.defense_record.application.util.TimeFormatHelper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyRecordRankResponse {

    private String nickname;
    private Long rank;
    private Long solvedCount;
    private LocalDateTime updatedAt;
    private String totalSolvedTime;
    private List<DailyDetailRankResponse> rankDetails;

    public static DailyRecordRankResponse of(String nickname, Long rank, LocalDateTime updatedAt, Long totalSolvedTime, Long totalSolvedCount, List<DailyDetailRankResponse> rankDetails) {
        return DailyRecordRankResponse.builder()
                .nickname(nickname)
                .rank(rank)
                .updatedAt(updatedAt)
                .solvedCount(totalSolvedCount)
                .rankDetails(rankDetails)
                .totalSolvedTime(TimeFormatHelper.solvedTimeToString(totalSolvedTime))
                .build();
    }
    @Builder
    private DailyRecordRankResponse(String nickname, Long rank, Long solvedCount, LocalDateTime updatedAt, String totalSolvedTime, List<DailyDetailRankResponse> rankDetails) {
        this.nickname = nickname;
        this.rank = rank;
        this.solvedCount = solvedCount;
        this.updatedAt = updatedAt;
        this.totalSolvedTime = totalSolvedTime;
        this.rankDetails = rankDetails;
    }
}
