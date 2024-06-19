package kr.co.morandi.backend.judgement.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SubmitErrorCode implements ErrorCode {

    LANGUAGE_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "언어 코드를 찾을 수 없습니다."),
    BAEKJOON_SUBMIT_PAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "백준 제출 페이지를 가져오는 중 오류가 발생했습니다."),
    CANT_FIND_SOLUTION_ID(HttpStatus.INTERNAL_SERVER_ERROR, "제출 결과 페이지에서 솔루션 ID를 찾을 수 없습니다."),
    BAEKJOON_SUBMIT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "백준 제출 중 오류가 발생했습니다."),
    CSRF_KEY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "제출 페이지에서 CSRF 키를 찾을 수 없습니다."),
    REDIRECTION_LOCATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "제출 후 리다이렉션 위치를 찾을 수 없습니다."),

    //제출 결과 저장 시 BAD_REQUEST
    SOURCE_CODE_NOT_FOUND(HttpStatus.BAD_REQUEST, "제출할 소스코드가 비어있습니다."),
    SUBMIT_VISIBILITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "제출 공개 여부를 찾을 수 없습니다."),
    PROBLEM_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "문제 번호를 찾을 수 없습니다."),
    DEFENSE_SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, "디펜스 세션을 찾을 수 없습니다."),
    LANGUAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "언어를 찾을 수 없습니다."),
    VISIBILITY_NOT_NULL(HttpStatus.BAD_REQUEST, "공개 여부는 null이 될 수 없습니다."),

    DETAIL_IS_NULL(HttpStatus.BAD_REQUEST, "DEFENSE DETAIL 값이 null입니다."),
    PROBLEM_NUMBER_IS_NULL(HttpStatus.BAD_REQUEST, "문제 번호가 null입니다."),

    PROBLEM_NUMBER_IS_NEGATIVE(HttpStatus.BAD_REQUEST, "문제 번호가 음수입니다."),
    VISIBILITY_IS_NULL(HttpStatus.BAD_REQUEST, "공개 여부가 null입니다."),
    INVALID_VISIBILITY_VALUE(HttpStatus.BAD_REQUEST, "공개 여부는 OPEN 또는 CLOSE이어야 합니다."),
    SOURCE_CODE_IS_NULL(HttpStatus.BAD_REQUEST, "제출 코드가 null일 수 없습니다."),
    TRIAL_NUMBER_IS_NULL(HttpStatus.BAD_REQUEST, "시도 횟수가 null일 수 없습니다."),
    TRIAL_NUMBER_IS_NEGATIVE(HttpStatus.BAD_REQUEST, "시도 횟수가 음수입니다."),
    SUBMIT_DATE_TIME_IS_NULL(HttpStatus.BAD_REQUEST, "제출 시간이 null일 수 없습니다.");



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

