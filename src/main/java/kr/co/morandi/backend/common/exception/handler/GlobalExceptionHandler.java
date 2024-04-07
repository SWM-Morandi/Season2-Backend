package kr.co.morandi.backend.common.exception.handler;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.CommonErrorCode;
import kr.co.morandi.backend.common.exception.errorcode.global.ErrorCode;
import kr.co.morandi.backend.common.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.net.URI;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${oauth2.cookie.domain}")
    private String domain;

    @Value("${oauth2.cookie.path}")
    private String path;

    @Value("${oauth2.signup-url}")
    private String signupPath;

    @ExceptionHandler(MorandiException.class)
    public ResponseEntity<ErrorResponse> MorandiExceptionHandler(MorandiException e) {
        if (e.getErrorCode().getHttpStatus() == HttpStatus.UNAUTHORIZED) {
            HttpHeaders headers = createUnauthorizedHeaders();
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return handleExceptionInternal(e.getErrorCode());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException() {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }
    private HttpHeaders createUnauthorizedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(signupPath));
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0); // 쿠키 삭제
        cookie.setPath(path);
        cookie.setDomain(domain);
        headers.add("Set-Cookie", cookie.toString());
        return headers;
    }
    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}
