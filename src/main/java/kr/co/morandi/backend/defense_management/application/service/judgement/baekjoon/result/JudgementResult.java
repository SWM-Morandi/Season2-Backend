package kr.co.morandi.backend.defense_management.application.service.judgement.baekjoon.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JudgementResult {

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


}