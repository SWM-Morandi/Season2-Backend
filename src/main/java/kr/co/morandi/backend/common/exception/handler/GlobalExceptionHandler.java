package kr.co.morandi.backend.common.exception.handler;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import kr.co.morandi.backend.common.exception.response.ErrorResponse;
import kr.co.morandi.backend.member_management.infrastructure.config.cookie.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static kr.co.morandi.backend.common.exception.handler.exception.CommonErrorCode.INTERNAL_SERVER_ERROR;
import static kr.co.morandi.backend.member_management.infrastructure.config.jwt.constants.TokenType.REFRESH_TOKEN;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final CookieUtils cookieUtils;

    @ExceptionHandler(MorandiException.class)
    public ResponseEntity<ErrorResponse> morandiExceptionHandler(MorandiException e) {
        log.error(e.getErrorCode().name()+" : ", e.getErrorCode().getMessage() + " : ", e);

        // Unauthorized 에러가 발생한 경우
        if (e.getErrorCode().getHttpStatus() == HttpStatus.UNAUTHORIZED) {
            HttpHeaders headers = createUnauthorizedHeaders();

            return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
        }

        // 그 외의 에러가 발생한 경우
        return handleException(e.getErrorCode());
    }

    /**
     * 예상 외의 에러가 발생한 경우
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception e) {
        log.error(INTERNAL_SERVER_ERROR.name() + " : ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    /**
     * Unauthorized 에러가 발생한 경우
     * Refresh Token 쿠키를 제거하고
     */
    private HttpHeaders createUnauthorizedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        Cookie cookie = cookieUtils.removeCookie(REFRESH_TOKEN, null);
        headers.add("Set-Cookie", cookie.toString());
        return headers;
    }

    /**
     * 에러 응답 생성
     */
    private ResponseEntity<ErrorResponse> handleException(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode));
    }

}
