package kr.co.morandi.backend.domain.exammanagement.management.request;

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
