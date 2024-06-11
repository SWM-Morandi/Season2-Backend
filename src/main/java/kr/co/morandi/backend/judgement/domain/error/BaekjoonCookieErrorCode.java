package kr.co.morandi.backend.judgement.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaekjoonCookieErrorCode implements ErrorCode {

    INVALID_COOKIE_VALUE(HttpStatus.BAD_REQUEST, "쿠키 값은 null이거나 빈 문자열일 수 없습니다."),
    ALREADY_LOGGED_OUT(HttpStatus.BAD_REQUEST, "이미 로그아웃된 쿠키입니다."),
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
