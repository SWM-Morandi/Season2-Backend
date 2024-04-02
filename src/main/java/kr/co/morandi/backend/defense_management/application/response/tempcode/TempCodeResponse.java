package kr.co.morandi.backend.defense_management.application.response.tempcode;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempCodeResponse {

    private Language language;
    private String code;

    @Builder
    private TempCodeResponse(Language language, String code) {
        this.language = language;
        this.code = code;
    }
}
