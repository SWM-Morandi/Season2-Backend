package kr.co.morandi.backend.judgement.application.request;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgementServiceRequest {

    private Long defenseSessionId;
    private Long memberId;
    private Long problemNumber;
    private Language language;
    private String sourceCode;
    private String submitVisibility;

}

