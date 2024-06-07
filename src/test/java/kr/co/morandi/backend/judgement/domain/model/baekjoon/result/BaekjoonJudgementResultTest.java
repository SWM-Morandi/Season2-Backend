package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonJudgementResultTest {

    @DisplayName("BaekjoonCorrectInfo default 객체 생성 테스트")
    @Test
    void createDefaultCorrectInfo() {
        // when
        BaekjoonJudgementResult defaultResult = BaekjoonJudgementResult.defaultResult();

        // then
        assertThat(defaultResult)
                .extracting("subtaskScore", "partialScore", "ac", "tot")
                .contains(0, 0, 0, 0);
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 subtaskScore null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullSubtaskScore() {
        // given
        Integer subtaskScore = null;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.subtaskScoreFrom(subtaskScore))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.SUBTASK_SCORE_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 subtaskScore 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeSubtaskScore() {
        // given
        Integer subtaskScore = -1;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.subtaskScoreFrom(subtaskScore))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.SUBTASK_SCORE_IS_NEGATIVE.getMessage());
    }
    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 partialScore가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullPartialScore() {
        // given
        Integer partialScore = null;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.partialScoreFrom(partialScore))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.PARTIAL_SCORE_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 partialScore가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativePartialScore() {
        // given
        Integer partialScore = -1;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.partialScoreFrom(partialScore))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.PARTIAL_SCORE_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullAcTot() {
        // given
        Integer ac = null;
        Integer tot = 10;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.acTotOf(ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 tot가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullTot() {
        // given
        Integer ac = 0;
        Integer tot = null;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.acTotOf(ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeAc() {
        // given
        Integer ac = -1;
        Integer tot = 10;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.acTotOf(ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 tot가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeTot() {
        // given
        Integer ac = 0;
        Integer tot = -1;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.acTotOf(ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 tot보다 큰 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithAcGreaterThanTot() {
        // given
        Integer ac = 10;
        Integer tot = 5;

        // when, then
        assertThatThrownBy(() -> BaekjoonJudgementResult.acTotOf(ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_GREATER_THAN_TOT.getMessage());
    }

    @DisplayName("defaultResult로 생성한 BaekjoonCorrectInfo 객체의 subtaskScore, partialScore, ac, tot 값 확인 테스트")
    @Test
    void defaultResult() {
        // given


        // when
        BaekjoonJudgementResult defaultResult = BaekjoonJudgementResult.defaultResult();

        // then
        assertThat(defaultResult)
                .extracting("subtaskScore", "partialScore", "ac", "tot")
                .contains(0, 0, 0, 0);

    }

    @DisplayName("subtaskScoreFrom으로 생성한 BaekjoonCorrectInfo 객체의 subtaskScore 값 확인 테스트")
    @Test
    void subtaskScoreFrom() {
        // given
        Integer subtaskScore = 10;

        // when
        BaekjoonJudgementResult subtaskScoreResult = BaekjoonJudgementResult.subtaskScoreFrom(subtaskScore);

        // then
        assertThat(subtaskScoreResult)
                .extracting("subtaskScore")
                .isEqualTo(10);
    }

    @DisplayName("partialScoreFrom으로 생성한 BaekjoonCorrectInfo 객체의 partialScore 값 확인 테스트")
    @Test
    void partialScoreFrom() {
        // given
        Integer partialScore = 10;

        // when
        BaekjoonJudgementResult partialScoreResult = BaekjoonJudgementResult.partialScoreFrom(partialScore);

        // then
        assertThat(partialScoreResult)
                .extracting("partialScore")
                .isEqualTo(10);
    }

    @DisplayName("acTotOf으로 생성한 BaekjoonCorrectInfo 객체의 ac, tot 값 확인 테스트")
    @Test
    void acTotOf() {
        // given
        Integer ac = 10;
        Integer tot = 20;

        // when
        BaekjoonJudgementResult acTotResult = BaekjoonJudgementResult.acTotOf(ac, tot);

        // then
        assertThat(acTotResult)
                .extracting("ac", "tot")
                .contains(10, 20);
    }

}