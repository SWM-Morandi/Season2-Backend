package kr.co.morandi.backend.defense_management.domain.error;

import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JudgementErrorCode implements ErrorCode {

    LANGUAGE_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "언어 코드를 찾을 수 없습니다."),
    BAEKJOON_SUBMIT_PAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "백준 제출 페이지를 가져오는 중 오류가 발생했습니다."),
    CANT_FIND_SOLUTION_ID(HttpStatus.INTERNAL_SERVER_ERROR, "제출 결과 페이지에서 솔루션 ID를 찾을 수 없습니다."),
    BAEKJOON_SUBMIT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "백준 제출 중 오류가 발생했습니다."),
    CSRF_KEY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "제출 페이지에서 CSRF 키를 찾을 수 없습니다."),
    REDIRECTION_LOCATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "제출 후 리다이렉션 위치를 찾을 수 없습니다.");



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

