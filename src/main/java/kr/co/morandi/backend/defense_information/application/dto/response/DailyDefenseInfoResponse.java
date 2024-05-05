package kr.co.morandi.backend.defense_information.application.dto.response;

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


    @Builder
    private DailyDefenseInfoResponse(String defenseName, Integer problemCount, Long attemptCount, List<DailyDefenseProblemInfoResponse> problems) {
        this.defenseName = defenseName;
        this.problemCount = problemCount;
        this.attemptCount = attemptCount;
        this.problems = problems;
    }
}
