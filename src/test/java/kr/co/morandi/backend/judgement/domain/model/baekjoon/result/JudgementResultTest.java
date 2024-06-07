package kr.co.morandi.backend.judgement.domain.model.baekjoon.result;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementResult;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JudgementResultTest {
    
    @DisplayName("JudgementResult reject 정적 팩토리 메서드 테스트")
    @Test
    void rejected() {
        // given
        JudgementStatus judgementStatus = JudgementStatus.WRONG_ANSWER;
        
        // when
        final JudgementResult judgementResult = JudgementResult.rejected(judgementStatus);
        
        // then
        assertThat(judgementResult).isNotNull()
                .extracting("judgementStatus", "memory", "time")
                .containsExactly(judgementStatus, 0, 0);
    }

    @DisplayName("JudgementResult 생성자에서 JudgementStatus가 null인 경우 예외 발생 테스트")
    @Test
    void constructorWithNullJudgementStatus() {
        // given

        // when & then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(null)
                .memory(0)
                .time(0)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.JUDGEMENT_RESULT_NOT_FOUND.getMessage());
    }

    @DisplayName("JudgementStatus가 ACCEPTED가 아닐 때 메모리 값이 0이 아닌 경우 예외 발생 테스트")
    @Test
    void validateCanExistMemory() {
        // given
        Integer memory = 1;
        Integer time = 0;

        // when & then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(JudgementStatus.WRONG_ANSWER)
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.NOT_ACCEPTED_CANNOT_HAVE_MEMORY_AND_TIME.getMessage());
    }

    @DisplayName("JudgementStatus가 ACCEPTED가 아닐 때 시간 값이 0이 아닌 경우 예외 발생 테스트")
    @Test
    void validateCanExistTime() {
        // given
        Integer memory = 0;
        Integer time = 1;

        // when & then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(JudgementStatus.WRONG_ANSWER)
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.NOT_ACCEPTED_CANNOT_HAVE_MEMORY_AND_TIME.getMessage());
    }

    @DisplayName("정상적인 JudgementResult 객체 생성 테스트")
    @Test
    void createCorrectInfo() {
        // given
        Integer memory = 0;
        Integer time = 0;

        // when
        final JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(JudgementStatus.ACCEPTED)
                .memory(memory)
                .time(time)
                .build();

        // then
        assertThat(judgementResult).isNotNull()
                .extracting("memory", "time")
                .containsExactly(memory, time);

    }

    @DisplayName("JudgementResult 객체 생성 시 memory가 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullMemory() {
        // given
        Integer memory = null;
        Integer time = 0;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(JudgementStatus.ACCEPTED)
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NULL.getMessage());
    }

    @DisplayName("JudgementResult 객체 생성 시 memory가 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeMemory() {
        // given
        Integer memory = -1;
        Integer time = 0;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(JudgementStatus.ACCEPTED)
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NEGATIVE.getMessage());
    }

    @DisplayName("JudgementResult 객체 생성 시 time이 null인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNullTime() {
        // given
        Integer memory = 0;
        Integer time = null;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(JudgementStatus.ACCEPTED)
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NULL.getMessage());
    }

    @DisplayName("JudgementResult 객체 생성 시 time이 음수인 경우 예외 발생 테스트")
    @Test
    void createCorrectInfoWithNegativeTime() {
        // given
        Integer memory = 0;
        Integer time = -1;

        // when, then
        assertThatThrownBy(() -> JudgementResult.builder()
                .judgementStatus(JudgementStatus.ACCEPTED)
                .memory(memory)
                .time(time)
                .build()
        )
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NEGATIVE.getMessage());
    }

}