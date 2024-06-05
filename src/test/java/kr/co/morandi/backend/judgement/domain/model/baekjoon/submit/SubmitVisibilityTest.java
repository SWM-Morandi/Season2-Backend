package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubmitVisibilityTest {
    @DisplayName("OPEN 값으로 SubmitVisibility 생성")
    @Test
    void fromOPEN() {
        // given
        String value = "OPEN";

        // when
        SubmitVisibility submitVisibility = SubmitVisibility.fromValue(value);

        // then
        assertThat(submitVisibility).isEqualTo(SubmitVisibility.OPEN);

    }
    @DisplayName("대소문자 구분없이 값으로 SubmitVisibility 생성")
    @Test
    void fromOpen() {
        // given
        String value = "Open";

        // when
        SubmitVisibility submitVisibility = SubmitVisibility.fromValue(value);

        // then
        assertThat(submitVisibility).isEqualTo(SubmitVisibility.OPEN);

    }

    @DisplayName("CLOSE 값으로 SubmitVisibility 생성")
    @Test
    void fromCLOSE() {
        // given
        String value = "CLOSE";

        // when
        SubmitVisibility submitVisibility = SubmitVisibility.fromValue(value);

        // then
        assertThat(submitVisibility).isEqualTo(SubmitVisibility.CLOSE);

    }
    @DisplayName("대소문자 구분없이 값으로 SubmitVisibility 생성")
    @Test
    void fromClose() {
        // given
        String value = "Close";

        // when
        SubmitVisibility submitVisibility = SubmitVisibility.fromValue(value);

        // then
        assertThat(submitVisibility).isEqualTo(SubmitVisibility.CLOSE);

    }

    @DisplayName("null 값으로 SubmitVisibility 생성")
    @Test
    void fromNull() {
        // given
        String value = null;

        // when & then
        assertThatThrownBy(() -> SubmitVisibility.fromValue(value))
                .isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.SUBMIT_VISIBILITY_NOT_FOUND.getMessage());

    }

    @DisplayName("빈 값으로 SubmitVisibility 생성")
    @Test
    void fromEmpty() {
        // given
        String value = "";

        // when & then
        assertThatThrownBy(() -> SubmitVisibility.fromValue(value))
                .isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.SUBMIT_VISIBILITY_NOT_FOUND.getMessage());

    }

    @DisplayName("잘못된 값으로 SubmitVisibility 생성")
    @Test
    void fromInvalidValue() {
        // given
        String value = "INVALID";

        // when & then
        assertThatThrownBy(() -> SubmitVisibility.fromValue(value))
                .isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.INVALID_VISIBILITY_VALUE.getMessage());

    }



}