package kr.co.morandi.backend.defense_management.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode {
    SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "세션을 찾을 수 없습니다."),
    SESSION_ALREADY_STARTED(HttpStatus.BAD_REQUEST, "이미 시작된 세션입니다."),
    SESSION_ALREADY_ENDED(HttpStatus.BAD_REQUEST, "이미 종료된 세션입니다."),
    INVALID_SESSION_OWNER(HttpStatus.FORBIDDEN, "사용자의 시험 세션이 아닙니다."),
    SESSION_CONNECTION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "디펜스 세션 연결에 실패하였습니다."),
    SESSION_TERMINATED(HttpStatus.BAD_REQUEST, "이미 종료된 디펜스 세션입니다.");
    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    @Override
    public String getMessage() {
        return message;
    }

    private final HttpStatus httpStatus;
    private final String message;
}
