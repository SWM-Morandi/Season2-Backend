package kr.co.morandi.backend.member_management.infrastructure.exception;


import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum OAuthErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED,"사용자를 찾을 수 없습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"인증 시간이 만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 오류" );
    private final HttpStatus httpStatus;
    private final String message;
}