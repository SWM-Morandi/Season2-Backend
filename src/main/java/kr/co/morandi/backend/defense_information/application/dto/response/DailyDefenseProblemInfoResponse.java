package kr.co.morandi.backend.defense_information.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyDefenseProblemInfoResponse {

    private Long problemNumber;
    private Long problemId;
    private Long baekjoonProblemId;
    private ProblemTier difficulty;
    private Long solvedCount;
    private Long submitCount;
    private Boolean isSolved;

    public static List<DailyDefenseProblemInfoResponse> ofNonAttempted(List<DailyDefenseProblem> dailyDefenseProblems) {
        return dailyDefenseProblems.stream().map(problem -> DailyDefenseProblemInfoResponse.builder()
                .problemNumber(problem.getProblemNumber())
                .problemId(problem.getProblem().getProblemId())
                .baekjoonProblemId(problem.getProblem().getBaekjoonProblemId())
                .difficulty(problem.getProblem().getProblemTier())
                .solvedCount(problem.getSolvedCount())
                .submitCount(problem.getSubmitCount())
                .build()
        ).toList();
    }
    public static List<DailyDefenseProblemInfoResponse> ofAttempted(List<DailyDefenseProblem> dailyDefenseProblems, Set<Long> solvedProblemNumbers) {
        return dailyDefenseProblems.stream().map(problem -> DailyDefenseProblemInfoResponse.builder()
                        .problemNumber(problem.getProblemNumber())
                        .problemId(problem.getProblem().getProblemId())
                        .baekjoonProblemId(problem.getProblem().getBaekjoonProblemId())
                        .difficulty(problem.getProblem().getProblemTier())
                        .solvedCount(problem.getSolvedCount())
                        .submitCount(problem.getSubmitCount())
                        .isSolved(solvedProblemNumbers.contains(problem.getProblemNumber()))
                        .build()
                ).toList();
    }
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
