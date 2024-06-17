package kr.co.morandi.backend.member_management.domain.model.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
