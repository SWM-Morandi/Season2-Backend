package kr.co.morandi.backend.defense_information.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyDefenseProblemInfoResponse {

    private Long problemNumber;
    private Long problemId;
    private Long baekjoonProblemId;
    private ProblemTier difficulty;
    private Long solvedCount;
    private Long submitCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isSolved;

    @Builder
    private DailyDefenseProblemInfoResponse(Long problemNumber, Long problemId, Long baekjoonProblemId, ProblemTier difficulty, Long solvedCount, Long submitCount, Boolean isSolved) {
        this.problemNumber = problemNumber;
        this.problemId = problemId;
        this.baekjoonProblemId = baekjoonProblemId;
        this.difficulty = difficulty;
        this.solvedCount = solvedCount;
        this.submitCount = submitCount;
        this.isSolved = isSolved;
    }
}


