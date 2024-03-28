package kr.co.morandi.backend.defense_management.application.request.session;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartDailyDefenseServiceRequest {

    private Long problemNumber;

    @Builder
    private StartDailyDefenseServiceRequest(LocalDateTime requestDateTime, Long problemNumber) {
        this.problemNumber = problemNumber;
    }
}
