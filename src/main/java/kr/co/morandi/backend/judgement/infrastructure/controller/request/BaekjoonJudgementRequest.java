package kr.co.morandi.backend.judgement.infrastructure.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.application.request.JudgementServiceRequest;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaekjoonJudgementRequest {

    @NotNull
    private Long defenseSessionId;

    @Positive
    private Long problemNumber;

    @NotNull
    private Language language;

    @NotNull
    private String sourceCode;

    @NotNull
    private SubmitVisibility submitVisibility;

    public JudgementServiceRequest toServiceRequest(Long memberId) {
        return JudgementServiceRequest.builder()
                .defenseSessionId(this.getDefenseSessionId())
                .memberId(memberId)
                .problemNumber(this.getProblemNumber())
                .language(this.getLanguage())
                .sourceCode(this.getSourceCode())
                .submitVisibility(this.getSubmitVisibility())
                .build();
    }
}
