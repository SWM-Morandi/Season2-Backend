package kr.co.morandi.backend.defense_management.domain.model.judgement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JudgementStatus {
    CORRECT(4, "맞았습니다!!"),
    WRONG_ANSWER(6, "틀렸습니다!!"),
    RUNTIME_ERROR(10, "런타임 에러"),
    COMPILE_ERROR(11, "컴파일 에러"),
    OTHER(0, "Other");

    private final int code;
    private final String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static JudgementStatus fromCode(int code) {
        for (JudgementStatus result : values()) {
            if (result.getCode() == code) {
                return result;
            }
        }
        return OTHER;
    }
}
