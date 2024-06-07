package kr.co.morandi.backend.judgement.infrastructure.helper;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonResultType;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementStatus;

public class JudgementStatusMapper {

    public static JudgementStatus mapToJudgementStatus(BaekjoonResultType baekjoonResultType) {
        return switch (baekjoonResultType) {
            case CORRECT -> JudgementStatus.ACCEPTED;
            case WRONG_ANSWER -> JudgementStatus.WRONG_ANSWER;
            case RUNTIME_ERROR -> JudgementStatus.RUNTIME_ERROR;
            case COMPILE_ERROR -> JudgementStatus.COMPILE_ERROR;
            case TIME_LIMIT_EXCEEDED -> JudgementStatus.TIME_LIMIT_EXCEEDED;
            case MEMORY_LIMIT_EXCEEDED -> JudgementStatus.MEMORY_LIMIT_EXCEEDED;
            default -> JudgementStatus.SUBMITTED;
        };
    }
}
