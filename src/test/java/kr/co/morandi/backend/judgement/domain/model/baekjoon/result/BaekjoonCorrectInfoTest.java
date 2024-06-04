package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonCorrectInfoTest {

    @DisplayName("정상적인 BaekjoonCorrectInfo 객체 생성 테스트")
    @Test
    void createCorrectInfo() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when
        final BaekjoonCorrectInfo baekjoonCorrectInfo = BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot);

        // then
        assertThat(baekjoonCorrectInfo).isNotNull()
                .extracting("memory", "time", "subtaskScore", "partialScore", "ac", "tot")
                .containsExactly(memory, time, subtaskScore, partialScore, ac, tot);

    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 memory가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullMemory() {
        // given
        Integer memory = null;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 memory가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeMemory() {
        // given
        Integer memory = -1;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 time이 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullTime() {
        // given
        Integer memory = 0;
        Integer time = null;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 time이 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeTime() {
        // given
        Integer memory = 0;
        Integer time = -1;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 subtaskScore가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullSubtaskScore() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = null;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.SUBTASK_SCORE_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 subtaskScore가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeSubtaskScore() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = -1.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.SUBTASK_SCORE_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 partialScore가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullPartialScore() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = null;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.PARTIAL_SCORE_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 partialScore가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativePartialScore() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = -1.0;
        Integer ac = 0;
        Integer tot = 0;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.PARTIAL_SCORE_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullAcTot() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = null;
        Integer tot = 10;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 tot가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullTot() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = null;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeAc() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = -1;
        Integer tot = 10;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 tot가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeTot() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 0;
        Integer tot = -1;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_OR_TOT_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 ac가 tot보다 큰 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithAcGreaterThanTot() {
        // given
        Integer memory = 0;
        Integer time = 0;
        Double subtaskScore = 0.0;
        Double partialScore = 0.0;
        Integer ac = 10;
        Integer tot = 5;

        // when, then
        assertThatThrownBy(() -> BaekjoonCorrectInfo.of(memory, time, subtaskScore, partialScore, ac, tot))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.AC_GREATER_THAN_TOT.getMessage());
    }


}