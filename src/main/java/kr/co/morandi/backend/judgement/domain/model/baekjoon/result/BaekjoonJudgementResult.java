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

    private Integer subtaskScore;
    private Integer partialScore;
    private Integer ac;
    private Integer tot;

    private static final Integer INITIAL_SUBTASK_SCORE = 0;
    private static final Integer INITIAL_PARTIAL_SCORE = 0;
    private static final Integer INITIAL_AC = 0;
    private static final Integer INITIAL_TOT = 0;

    public static BaekjoonJudgementResult defaultResult() {
        return new BaekjoonJudgementResult(INITIAL_SUBTASK_SCORE, INITIAL_PARTIAL_SCORE, INITIAL_AC, INITIAL_TOT);
    }

    public static BaekjoonJudgementResult subtaskScoreFrom(Integer subtaskScore) {
        return new BaekjoonJudgementResult(subtaskScore, INITIAL_PARTIAL_SCORE, INITIAL_AC, INITIAL_TOT);
    }

    public static BaekjoonJudgementResult partialScoreFrom(Integer partialScore) {
        return new BaekjoonJudgementResult(INITIAL_SUBTASK_SCORE, partialScore, INITIAL_AC, INITIAL_TOT);
    }

    public static BaekjoonJudgementResult acTotOf(Integer ac, Integer tot) {
        return new BaekjoonJudgementResult(INITIAL_SUBTASK_SCORE, INITIAL_PARTIAL_SCORE, ac, tot);
    }

    private BaekjoonJudgementResult(Integer subtaskScore, Integer partialScore, Integer ac, Integer tot) {
        validateSubtaskScore(subtaskScore);
        this.subtaskScore = subtaskScore;

        validatePartialScore(partialScore);
        this.partialScore = partialScore;

        validateAcTot(ac, tot);
        this.ac = ac;
        this.tot = tot;
    }

    private void validateSubtaskScore(Integer subtaskScore) {
        if(subtaskScore == null)
            throw new MorandiException(JudgementResultErrorCode.SUBTASK_SCORE_IS_NULL);
        if(subtaskScore < 0)
            throw new MorandiException(JudgementResultErrorCode.SUBTASK_SCORE_IS_NEGATIVE);
    }

    private void validatePartialScore(Integer partialScore) {
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
