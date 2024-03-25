package kr.co.morandi.backend.global.exception;

import kr.co.morandi.backend.global.exception.errorcode.ErrorCode;
import lombok.Getter;
@Getter
public class MorandiException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public MorandiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public MorandiException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
