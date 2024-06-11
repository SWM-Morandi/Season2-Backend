package kr.co.morandi.backend.judgement.domain.model.submit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JudgementStatus {

    ACCEPTED("ACCEPTED"),
    PARTIALLY_ACCEPTED("PARTIALLY_ACCEPTED"),
    WRONG_ANSWER("WRONG_ANSWER"),
    RUNTIME_ERROR("RUNTIME_ERROR"),
    COMPILE_ERROR("COMPILE_ERROR"),
    TIME_LIMIT_EXCEEDED("TIME_LIMIT_EXCEEDED"),
    MEMORY_LIMIT_EXCEEDED("MEMORY_LIMIT_EXCEEDED"),
    SUBMITTED("SUBMITTED");

    @Column(name = "judgement_status")
    private final String value;

    @JsonCreator
    public static JudgementStatus fromValue(String value) {
        for(JudgementStatus status : values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}
