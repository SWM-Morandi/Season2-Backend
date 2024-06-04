package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.ResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTypeTest {

    @DisplayName("정답입니다 테스트")
    @Test
    void fromCode4() {
        // given
        int code = 4;

        // when
        ResultType result = ResultType.fromCode(code);

        // then
        assertThat(result).isEqualTo(ResultType.CORRECT);
    }

    @DisplayName("틀렸습니다 테스트")
    @Test
    void fromCode6() {
        // given
        int code = 6;

        // when
        ResultType result = ResultType.fromCode(code);

        // then
        assertThat(result).isEqualTo(ResultType.WRONG_ANSWER);

    }
    @DisplayName("런타임 에러 테스트")
    @Test
    void fromCode10() {
        // given
        int code = 10;

        // when
        ResultType result = ResultType.fromCode(code);

        // then
        assertThat(result).isEqualTo(ResultType.RUNTIME_ERROR);

    }

    @DisplayName("컴파일 에러 테스트")
    @Test
    void fromCode11() {
        // given
        int code = 11;

        // when
        ResultType result = ResultType.fromCode(code);

        // then
        assertThat(result).isEqualTo(ResultType.COMPILE_ERROR);

    }

    @DisplayName("채점 중 테스트")
    @Test
    void fromCode3() {
        // given
        int code = 3;

        // when
        ResultType result = ResultType.fromCode(code);

        // then
        assertThat(result).isEqualTo(ResultType.PROGRESS);

    }

    @DisplayName("Other 테스트")
    @Test
    void fromCode0() {
        // given
        int code = 0;

        // when
        ResultType result = ResultType.fromCode(code);

        // then
        assertThat(result).isEqualTo(ResultType.OTHER);
    }



}