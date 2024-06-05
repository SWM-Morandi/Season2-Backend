package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import jakarta.persistence.Embeddable;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaekjoonJudgementResult {

    private Double subtaskScore;

    private Double partialScore;

    private Integer ac;

    private Integer tot;

    public static BaekjoonJudgementResult of(Double subtaskScore, Double partialScore, Integer ac, Integer tot) {
        return new BaekjoonJudgementResult(subtaskScore, partialScore, ac, tot);
    }

    private BaekjoonJudgementResult(Double subtaskScore, Double partialScore, Integer ac, Integer tot) {
        validateSubtaskScore(subtaskScore);
        this.subtaskScore = subtaskScore;

        validatePartialScore(partialScore);
        this.partialScore = partialScore;

        validateAcTot(ac, tot);
        this.ac = ac;
        this.tot = tot;
    }

    private void validateSubtaskScore(Double subtaskScore) {
        if(subtaskScore == null)
            throw new MorandiException(JudgementResultErrorCode.SUBTASK_SCORE_IS_NULL);
        if(subtaskScore < 0)
            throw new MorandiException(JudgementResultErrorCode.SUBTASK_SCORE_IS_NEGATIVE);
    }

    private void validatePartialScore(Double partialScore) {
        if(partialScore == null)
            throw new MorandiException(JudgementResultErrorCode.PARTIAL_SCORE_IS_NULL);
        if(partialScore < 0)
            throw new MorandiException(JudgementResultErrorCode.PARTIAL_SCORE_IS_NEGATIVE);
    }

    private void validateAcTot(Integer ac, Integer tot) {
        if(ac == null || tot == null)
            throw new MorandiException(JudgementResultErrorCode.AC_OR_TOT_IS_NULL);
        if(ac < 0 || tot < 0)
            throw new MorandiException(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE);
        if(ac > tot)
            throw new MorandiException(JudgementResultErrorCode.AC_GREATER_THAN_TOT);

    }
}
