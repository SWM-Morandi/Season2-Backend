package kr.co.morandi.backend.web.defensemanagement.dailydefense.dto.request;

import kr.co.morandi.backend.domain.defensemanagement.management.request.StartDailyDefenseServiceRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartDailyDefenseRequest {

    private Long problemNumber;

    @Builder
    private StartDailyDefenseRequest(Long problemNumber) {
        this.problemNumber = problemNumber;
    }

    public StartDailyDefenseServiceRequest toServiceRequest() {
        return StartDailyDefenseServiceRequest.builder()
                .problemNumber(problemNumber)
                .build();
    }
}
