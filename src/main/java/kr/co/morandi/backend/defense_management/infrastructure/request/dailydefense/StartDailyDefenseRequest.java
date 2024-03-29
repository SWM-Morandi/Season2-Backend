package kr.co.morandi.backend.defense_management.infrastructure.request.dailydefense;

import kr.co.morandi.backend.defense_management.application.request.session.StartDailyDefenseServiceRequest;
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
