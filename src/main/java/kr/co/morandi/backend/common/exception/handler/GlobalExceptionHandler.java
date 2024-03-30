package kr.co.morandi.backend.common.exception.handler;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.CommonErrorCode;
import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import kr.co.morandi.backend.common.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
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
    @ExceptionHandler(MorandiException.class)
    public ResponseEntity<?> MorandiExceptionHandler(MorandiException e) {
        String message = e.getMessage();
        if (e.getErrorCode().getHttpStatus() == HttpStatus.UNAUTHORIZED) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("https://morandi.co.kr/signup"));

            Cookie cookie = new Cookie("accessToken", null);
            cookie.setMaxAge(0); // 쿠키 삭제
            cookie.setPath("/");
            cookie.setDomain("morandi.co.kr");

            headers.add("Set-Cookie", cookie.toString());
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return handleExceptionInternal(e.getErrorCode(), message);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException(Exception e) {
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }
    private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }
}
