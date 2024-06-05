package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonJudgementResultTest {

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 subtaskScore null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullSubtaskScore() {
        // given
        Double subtaskScore = null;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.SUBTASK_SCORE_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 subtaskScore 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeSubtaskScore() {
        // given
        Double subtaskScore = -1.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.SUBTASK_SCORE_IS_NEGATIVE.getMessage());
    }
    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 partialScore가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullPartialScore() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = null;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.PARTIAL_SCORE_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 partialScore가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativePartialScore() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = -1.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.PARTIAL_SCORE_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullAcTot() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = null;
        Integer tot = 10;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 tot가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullTot() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = null;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeAc() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = -1;
        Integer tot = 10;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 tot가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeTot() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = -1;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 tot보다 큰 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithAcGreaterThanTot() {
        // given
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 10;
        Integer tot = 5;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.of(subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_GREATER_THAN_TOT.getMessage());
    }

}