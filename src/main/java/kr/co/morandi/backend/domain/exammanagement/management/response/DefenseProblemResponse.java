package kr.co.morandi.backend.domain.exammanagement.management.response;

import kr.co.morandi.backend.domain.exammanagement.tempcode.model.Language;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefenseProblemResponse {

    private Long problemId;
    private Long problemNumber;
    private Long baekjoonProblemId;
    private boolean isCorrect;
    private Language tempCodeLanguage;
    private String tempCode;

    @Builder
    private DefenseProblemResponse(Long problemId, Long problemNumber, Long baekjoonProblemId, boolean isCorrect,
                                  Language tempCodeLanguage, String tempCode) {
        this.problemId = problemId;
        this.problemNumber = problemNumber;
        this.baekjoonProblemId = baekjoonProblemId;
        this.isCorrect = isCorrect;
        this.tempCodeLanguage = tempCodeLanguage;
        this.tempCode = tempCode;
    }
}
