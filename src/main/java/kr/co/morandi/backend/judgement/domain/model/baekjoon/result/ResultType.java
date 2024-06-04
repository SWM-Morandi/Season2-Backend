package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ResultType {
    CORRECT(4, "맞았습니다!!"),
    WRONG_ANSWER(6, "틀렸습니다!!"),
    RUNTIME_ERROR(10, "런타임 에러"),
    COMPILE_ERROR(11, "컴파일 에러"),
    PROGRESS(3, "채점 중"),
    COMPILE_PROGRESS(2, "컴파일 중"),
    TIME_LIMIT_EXCEEDED(7, "시간 초과"),
    MEMORY_LIMIT_EXCEEDED(8, "메모리 초과"),
    OUTPUT_LIMIT_EXCEEDED(9, "출력 초과"),
    OUTPUT_FORMAT_ERROR(5, "출력 형식이 잘못되었습니다."),
    SUBMITTED(-1, "제출됨"),
    OTHER(0, "Other");

    private final int code;
    private final String description;

    private static final Map<Integer, ResultType> valueMap = Arrays.stream(values())
            .collect(Collectors.toMap(ResultType::getCode, e -> e));
    @JsonValue
    public int getCode() {
        return code;
    }
    @JsonCreator
    public static ResultType fromCode(Integer code) {
        if(code == null)
            throw new MorandiException(JudgementResultErrorCode.RESULT_CODE_IS_NULL);
        return valueMap.getOrDefault(code, OTHER);
    }
}