package kr.co.morandi.backend.judgement.application.request;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgementServiceRequest {

    private Long defenseSessionId;
    private Long memberId;
    private Long problemNumber;
    private Language language;
    private String sourceCode;
    private SubmitVisibility submitVisibility;
    private LocalDateTime nowDateTime;

    @Builder
    private JudgementServiceRequest(Long defenseSessionId, Long memberId, Long problemNumber, Language language, String sourceCode, SubmitVisibility submitVisibility, LocalDateTime nowDateTime) {
        this.defenseSessionId = defenseSessionId;
        this.memberId = memberId;
        this.problemNumber = problemNumber;
        this.language = language;
        this.sourceCode = sourceCode;
        this.submitVisibility = submitVisibility;
        this.nowDateTime = nowDateTime;
    }
}

