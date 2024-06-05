package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SourceCodeTest {

    @DisplayName("SourceCode 객체를 생성할 수 있다.")
    @Test
    void sourceCodeOf() {
        // given
        String source = "sourceCode";

        // when
        SubmitCode submitCode = SubmitCode.of(source, Language.JAVA);

        // then
        assertThat(submitCode)
                .isNotNull()
                .extracting("sourceCode", "language")
                .containsExactly(source, Language.JAVA);

    }

    @DisplayName("SourceCode 객체를 생성할 때 source가 null이면 예외가 발생한다.")
    @Test
    void sourceCodeOfWithNullSource() {
        // given
        String source = null;

        // when & then
        assertThatThrownBy(() -> SubmitCode.of(source, Language.JAVA))
                .isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.SOURCE_CODE_NOT_FOUND.getMessage());
    }

    @DisplayName("SourceCode 객체를 생성할 때 source가 빈 문자열이면 예외가 발생한다.")
    @Test
    void sourceCodeOfWithEmptySource() {
        // given
        String source = "";

        // when & then
        assertThatThrownBy(() -> SubmitCode.of(source, Language.JAVA))
                .isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.SOURCE_CODE_NOT_FOUND.getMessage());
    }



}