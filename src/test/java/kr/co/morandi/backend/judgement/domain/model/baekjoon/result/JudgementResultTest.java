package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JudgementResultTest {

    @DisplayName("정상적인 BaekjoonCorrectInfo 객체 생성 테스트")
    @Test
    void createCorrectInfo() {
        // given
        Integer memory = 0;
        Integer time = 0;

        // when
        final JudgementResult judgementResult = JudgementResult.builder()
                .memory(memory)
                .time(time)
                .build();

        // then
        assertThat(judgementResult).isNotNull()
                .extracting("memory", "time")
                .containsExactly(memory, time);

    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 memory가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullMemory() {
        // given
        Integer memory = null;
        Integer time = 0;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .memory(memory)
                .time(time)
                
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 memory가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeMemory() {
        // given
        Integer memory = -1;
        Integer time = 0;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NEGATIVE.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 time이 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullTime() {
        // given
        Integer memory = 0;
        Integer time = null;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NULL.getMessage());
    }

    @DisplayName("BaekjoonCorrectInfo 객체 생성 시 time이 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeTime() {
        // given
        Integer memory = 0;
        Integer time = -1;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NEGATIVE.getMessage());
    }

}