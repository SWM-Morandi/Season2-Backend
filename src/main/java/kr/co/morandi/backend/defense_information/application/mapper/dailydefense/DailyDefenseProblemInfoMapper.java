package kr.co.morandi.backend.defense_information.application.mapper.dailydefense;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseProblemInfoResponse;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyDefenseProblemInfoMapper {

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
}
