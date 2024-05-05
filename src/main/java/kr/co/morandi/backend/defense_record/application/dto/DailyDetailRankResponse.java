package kr.co.morandi.backend.defense_record.application.dto;

import kr.co.morandi.backend.defense_record.application.util.TimeFormatHelper;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyDetailRankResponse {

    private Long problemNumber;
    private Boolean isSolved;
    private String solvedTime;

    public static List<DailyDetailRankResponse> of(List<DailyDetail> dailyDetails) {
        return dailyDetails.stream()
                    .map(details -> DailyDetailRankResponse.builder()
                            .problemNumber(details.getProblemNumber())
                            .isSolved(details.getIsSolved())
                            .solvedTime(TimeFormatHelper.solvedTimeToString(details.getSolvedTime()))
                            .build())
                    .toList();
    }

    @Builder
    private DailyDetailRankResponse(Long problemNumber, Boolean isSolved, String solvedTime) {
        this.problemNumber = problemNumber;
        this.isSolved = isSolved;
        this.solvedTime = solvedTime;
    }
}
