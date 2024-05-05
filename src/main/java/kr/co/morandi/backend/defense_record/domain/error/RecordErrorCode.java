package kr.co.morandi.backend.defense_record.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecordErrorCode implements ErrorCode {
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "시험 기록(Record)를 찾을 수 없습니다."),
    RECORD_ALREADY_TERMINATED(HttpStatus.BAD_REQUEST, "이미 종료된 시험 기록입니다.")
    ;
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
