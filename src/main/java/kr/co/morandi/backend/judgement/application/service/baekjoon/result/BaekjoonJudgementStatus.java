package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.ResultType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaekjoonJudgementStatus {

    @JsonProperty("result")
    private ResultType result;

    @JsonProperty("progress")
    private Integer progress;

    @JsonProperty("memory")
    private Integer memory;

    @JsonProperty("time")
    private Integer time;

    @JsonProperty("subtask_score")
    private Double subtaskScore;

    @JsonProperty("partial_score")
    private Double partialScore;

    @JsonProperty("ac")
    private Integer ac;

    @JsonProperty("tot")
    private Integer tot;

    @JsonProperty("feedback")
    private String feedback;

    @JsonProperty("rte_reason")
    private String rteReason;

    @JsonProperty("remain")
    private Integer remain;

    public boolean isAccepted() {
        return this.getResult().equals(ResultType.CORRECT);
    }

    public boolean isRejected() {
        return this.getResult().equals(ResultType.WRONG_ANSWER) || this.getResult().equals(ResultType.RUNTIME_ERROR)
                || this.getResult().equals(ResultType.COMPILE_ERROR) || this.getResult().equals(ResultType.TIME_LIMIT_EXCEEDED)
                || this.getResult().equals(ResultType.OTHER);
    }

    public boolean isFinalResult() {
        ResultType resultType = this.getResult();
        return resultType.equals(ResultType.CORRECT) || resultType.equals(ResultType.WRONG_ANSWER)
                || resultType.equals(ResultType.RUNTIME_ERROR) || resultType.equals(ResultType.COMPILE_ERROR)
                || resultType.equals(ResultType.TIME_LIMIT_EXCEEDED) || resultType.equals(ResultType.OTHER);
    }


}