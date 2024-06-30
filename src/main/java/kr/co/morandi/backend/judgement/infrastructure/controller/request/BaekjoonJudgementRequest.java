package kr.co.morandi.backend.judgement.infrastructure.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.application.request.JudgementServiceRequest;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaekjoonJudgementRequest {

    @NotNull(message = "defenseSessionId가 존재해야 합니다.")
    @Positive(message = "defenseSessionId는 양수여야 합니다.")
    private Long defenseSessionId;

    @NotNull(message = "problemNumber가 존재해야 합니다.")
    @Positive(message = "problemNumber가 양수여야 합니다.")
    private Long problemNumber;

    @NotNull(message = "language가 존재해야 합니다.")
    private Language language;

    @NotEmpty(message = "sourceCode가 존재해야 합니다.")
    private String sourceCode;

    @NotNull(message = "submitVisibility가 존재해야 합니다.")
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

    @Builder
    private BaekjoonJudgementRequest(Long defenseSessionId, Long problemNumber, Language language, String sourceCode, SubmitVisibility submitVisibility) {
        this.defenseSessionId = defenseSessionId;
        this.problemNumber = problemNumber;
        this.language = language;
        this.sourceCode = sourceCode;
        this.submitVisibility = submitVisibility;
    }
}
