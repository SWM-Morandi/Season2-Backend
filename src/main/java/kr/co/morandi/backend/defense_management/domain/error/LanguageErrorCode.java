package kr.co.morandi.backend.defense_management.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LanguageErrorCode implements ErrorCode {

    LANGUAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 언어 값입니다.");

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
