package kr.co.morandi.backend.common.exception;

import kr.co.morandi.backend.common.exception.errorcode.global.ErrorCode;
import lombok.Getter;
@Getter
public class MorandiException extends RuntimeException {

    private final ErrorCode errorCode;

    public MorandiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public MorandiException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
    }
}
