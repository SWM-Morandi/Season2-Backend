package kr.co.morandi.backend.judgement.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaekjoonCookieErrorCode implements ErrorCode {

    INVALID_COOKIE_VALUE(HttpStatus.BAD_REQUEST, "쿠키 값을 생성하는데 필요한 정보가 부족합니다."),
    ALREADY_LOGGED_OUT(HttpStatus.BAD_REQUEST, "이미 로그아웃된 쿠키입니다."),

    // 관리자
    INVALID_GLOBAL_USER_ID(HttpStatus.BAD_REQUEST, "글로벌 유저 아이디는 null이거나 빈 문자열일 수 없습니다."),
    INVALID_BAEKJOON_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "백준 관리자 Refresh Token은 null이거나 빈 문자열일 수 없습니다."),
    NOT_EXIST_GLOBAL_COOKIE(HttpStatus.INTERNAL_SERVER_ERROR, "현재 유효한 관리 쿠키가 존재하지 않습니다. 관리자에게 문의하세요.");


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
