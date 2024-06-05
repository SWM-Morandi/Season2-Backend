package kr.co.morandi.backend.judgement.domain.model.submit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubmitVisibility {
    OPEN("OPEN"),
    CLOSE("CLOSE");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SubmitVisibility fromValue(String value) {
        if(value == null || value.isEmpty()) {
            throw new MorandiException(SubmitErrorCode.SUBMIT_VISIBILITY_NOT_FOUND);
        }
        for(SubmitVisibility submitVisibility : SubmitVisibility.values()) {
            if (submitVisibility.value.equals(value.toUpperCase())) {
                return submitVisibility;
            }
        }
        throw new MorandiException(SubmitErrorCode.INVALID_VISIBILITY_VALUE);
    }


}
