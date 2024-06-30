package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonResultTypeTest {

    @DisplayName("Null이 들어올 때 예외가 발생해야 한다.")
    @Test
    void fromCodeWithNullCode() {
        // given
        Integer code = null;

        // when & then
        assertThatThrownBy(() -> BaekjoonResultType.fromCode(code))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.RESULT_CODE_IS_NULL.getMessage());
    }

    @DisplayName("정답입니다 테스트")
    @Test
    void fromCode4() {
        // given
        int code = 4;

        // when
        BaekjoonResultType result = BaekjoonResultType.fromCode(code);

        // then
        assertThat(result)
                .extracting("code", "description")
                .contains(4, "맞았습니다!!");
    }

    @DisplayName("틀렸습니다 테스트")
    @Test
    void fromCode6() {
        // given
        int code = 6;

        // when
        BaekjoonResultType result = BaekjoonResultType.fromCode(code);

        // then
        assertThat(result)
                .extracting("code", "description")
                .contains(6, "틀렸습니다!!");

    }
    @DisplayName("런타임 에러 테스트")
    @Test
    void fromCode10() {
        // given
        int code = 10;

        // when
        BaekjoonResultType result = BaekjoonResultType.fromCode(code);

        // then
        assertThat(result)
                .extracting("code", "description")
                .contains(10, "런타임 에러");
    }

    @DisplayName("컴파일 에러 테스트")
    @Test
    void fromCode11() {
        // given
        int code = 11;

        // when
        BaekjoonResultType result = BaekjoonResultType.fromCode(code);

        // then
        assertThat(result)
                .extracting("code", "description")
                .contains(11, "컴파일 에러");

    }

    @DisplayName("채점 중 테스트")
    @Test
    void fromCode3() {
        // given
        int code = 3;

        // when
        BaekjoonResultType result = BaekjoonResultType.fromCode(code);

        // then
        assertThat(result)
                .extracting("code", "description")
                .contains(3, "채점 중");
    }

    @DisplayName("Other 테스트")
    @Test
    void fromCode0() {
        // given
        int code = 0;

        // when
        BaekjoonResultType result = BaekjoonResultType.fromCode(code);

        // then
        assertThat(result)
                .extracting("code", "description")
                .contains(0, "Other");
    }

}