package kr.co.morandi.backend.common.exception.response;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
    @Builder
    private ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}