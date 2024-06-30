package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonResultType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaekjoonJudgementStatus {

    @JsonProperty("result")
    private BaekjoonResultType result;

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
        return this.getResult().equals(BaekjoonResultType.CORRECT);
    }

    public boolean isRejected() {
        return this.getResult().equals(BaekjoonResultType.WRONG_ANSWER) || this.getResult().equals(BaekjoonResultType.RUNTIME_ERROR)
                || this.getResult().equals(BaekjoonResultType.COMPILE_ERROR) || this.getResult().equals(BaekjoonResultType.TIME_LIMIT_EXCEEDED)
                || this.getResult().equals(BaekjoonResultType.OTHER);
    }

    public boolean isFinalResult() {
        BaekjoonResultType baekjoonResultType = this.getResult();
        return baekjoonResultType.equals(BaekjoonResultType.CORRECT) || baekjoonResultType.equals(BaekjoonResultType.WRONG_ANSWER)
                || baekjoonResultType.equals(BaekjoonResultType.RUNTIME_ERROR) || baekjoonResultType.equals(BaekjoonResultType.COMPILE_ERROR)
                || baekjoonResultType.equals(BaekjoonResultType.TIME_LIMIT_EXCEEDED) || baekjoonResultType.equals(BaekjoonResultType.OTHER);
    }

    @Builder
    private BaekjoonJudgementStatus(BaekjoonResultType result, Integer progress, Integer memory, Integer time,
                                   Double subtaskScore, Double partialScore, Integer ac, Integer tot, String feedback, String rteReason, Integer remain) {
        this.result = result;
        this.progress = progress;
        this.memory = memory;
        this.time = time;
        this.subtaskScore = subtaskScore;
        this.partialScore = partialScore;
        this.ac = ac;
        this.tot = tot;
        this.feedback = feedback;
        this.rteReason = rteReason;
        this.remain = remain;
    }
}