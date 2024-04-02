package kr.co.morandi.backend.defense_management.application.response.session;

import kr.co.morandi.backend.defense_management.application.response.tempcode.TempCodeResponse;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefenseProblemResponse {

    private Long problemId;
    private Long problemNumber;
    private Long baekjoonProblemId;
    private boolean isCorrect;
    private Language lastAccessLanguage;
    private Set<TempCodeResponse> tempCodes;

    @Builder
    private DefenseProblemResponse(Long problemId, Long problemNumber, Long baekjoonProblemId,
                                   boolean isCorrect, Language lastAccessLanguage, Set<TempCodeResponse> tempCodes) {
        this.problemId = problemId;
        this.problemNumber = problemNumber;
        this.baekjoonProblemId = baekjoonProblemId;
        this.isCorrect = isCorrect;
        this.lastAccessLanguage = lastAccessLanguage;
        this.tempCodes = tempCodes;
    }
}
