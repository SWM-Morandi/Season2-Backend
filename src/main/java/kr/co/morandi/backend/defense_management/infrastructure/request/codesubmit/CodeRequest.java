package kr.co.morandi.backend.defense_management.infrastructure.request.codesubmit;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CodeRequest {

    private String code;
    private String language;
    private String input;
    private String sseId;
}
