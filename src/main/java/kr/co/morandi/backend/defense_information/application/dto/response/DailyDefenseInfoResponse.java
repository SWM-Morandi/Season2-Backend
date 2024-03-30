package kr.co.morandi.backend.defense_information.application.dto.response;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyDefenseInfoResponse {

    private String defenseName;
    private Integer problemCount;
    private Long attemptCount;
    private List<DailyDefenseProblemInfoResponse> problems;


    public static DailyDefenseInfoResponse fromNonAttempted(DailyDefense dailyDefense) {
        return DailyDefenseInfoResponse.builder()
                .defenseName(dailyDefense.getContentName())
                .problemCount(dailyDefense.getProblemCount())
                .attemptCount(dailyDefense.getAttemptCount())
                .problems(DailyDefenseProblemInfoResponse.ofNonAttempted(dailyDefense.getDailyDefenseProblems()))
                .build();
    }

    public static DailyDefenseInfoResponse ofAttempted(DailyDefense dailyDefense, DailyRecord dailyRecord) {
        return DailyDefenseInfoResponse.builder()
                .defenseName(dailyDefense.getContentName())
                .problemCount(dailyDefense.getProblemCount())
                .attemptCount(dailyDefense.getAttemptCount())
                .problems(DailyDefenseProblemInfoResponse.ofAttempted(
                                        dailyDefense.getDailyDefenseProblems(),
                                        dailyRecord.getSolvedProblemNumbers())
                )
                .build();
    }

    @Builder
    private DailyDefenseInfoResponse(String defenseName, Integer problemCount, Long attemptCount, List<DailyDefenseProblemInfoResponse> problems) {
        this.defenseName = defenseName;
        this.problemCount = problemCount;
        this.attemptCount = attemptCount;
        this.problems = problems;
    }
}
