package kr.co.morandi.backend.judgement.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JudgementResultErrorCode implements ErrorCode {
    JUDGEMENT_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 결과를 찾을 수 없습니다."),
    AC_GREATER_THAN_TOT(HttpStatus.INTERNAL_SERVER_ERROR, "ac가 tot보다 큽니다."),
    AC_OR_TOT_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "ac와 tot의 값이 null입니다."),
    AC_OR_TOT_IS_NEGATIVE(HttpStatus.INTERNAL_SERVER_ERROR, "ac와 tot의 값이 음수입니다."),

    MEMORY_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "memory 값이 null입니다."),
    MEMORY_IS_NEGATIVE(HttpStatus.INTERNAL_SERVER_ERROR, "memory 값이 음수입니다."),

    TIME_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "time 값이 null입니다."),
    TIME_IS_NEGATIVE(HttpStatus.INTERNAL_SERVER_ERROR, "time 값이 음수입니다."),

    SUBTASK_SCORE_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "subtaskScore 값이 null입니다."),
    SUBTASK_SCORE_IS_NEGATIVE(HttpStatus.INTERNAL_SERVER_ERROR, "subtaskScore 값이 올바르지 않습니다."),

    PARTIAL_SCORE_IS_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "partialScore 값이 null입니다."),
    PARTIAL_SCORE_IS_NEGATIVE(HttpStatus.INTERNAL_SERVER_ERROR, "partialScore 값이 올바르지 않습니다."),

    CORRECT_INFO_WHEN_NOT_CORRECT(HttpStatus.INTERNAL_SERVER_ERROR, "정답 상태가 아닐 경우 정답 정보는 초기상태여야 합니다."),

    RESULT_CODE_IS_NULL(HttpStatus.BAD_REQUEST, "결과 코드는 null일 수 없습니다"),


    RESULT_INFO_WHEN_CORRECT(HttpStatus.INTERNAL_SERVER_ERROR, "정답 상태일 때는 결과 정보를 업데이트 할 수 없습니다."),
    TRIAL_NUMBER_IS_NULL(HttpStatus.BAD_REQUEST, "시도 횟수는 null일 수 없습니다."),
    TRIAL_NUMBER_IS_NEGATIVE(HttpStatus.BAD_REQUEST, "시도 횟수는 음수일 수 없습니다."),
    ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, "이미 정답 처리된 결과입니다."),
    SUBMIT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 제출을 찾을 수 없습니다."),
    INVALID_JUDGEMENT_RESULT(HttpStatus.INTERNAL_SERVER_ERROR, "백준으로부터 받은 채점 결과 응답이 올바르지 않습니다.");


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
