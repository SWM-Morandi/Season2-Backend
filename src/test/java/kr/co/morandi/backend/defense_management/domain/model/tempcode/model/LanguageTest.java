package kr.co.morandi.backend.defense_management.domain.model.tempcode.model;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.domain.error.LanguageErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LanguageTest {
    @DisplayName("CPP에 해당하는 Language 객체를 반환한다.")
    @Test
    void fromCpp() {
        // given
        String value = "CPP";

        // when
        Language language = Language.from(value);

        // then
        assertThat(language).isEqualTo(Language.CPP);
    }

    @DisplayName("JAVA 해당하는 Language 객체를 반환한다.")
    @Test
    void fromJava() {
        // given
        String value = "JAVA";

        // when
        Language language = Language.from(value);

        // then
        assertThat(language).isEqualTo(Language.JAVA);
    }

    @DisplayName("PYTHON 해당하는 Language 객체를 반환한다.")
    @Test
    void fromPython() {
        // given
        String value = "PYTHON";

        // when
        Language language = Language.from(value);

        // then
        assertThat(language).isEqualTo(Language.PYTHON);
    }

    @DisplayName("적절하지 않은 Language 값을 입력하면 예외를 반환한다.")
    @Test
    void fromInvalidValue() {
        // given
        String value = "JAVAGOOD";

        // when & then
        assertThatThrownBy(() -> Language.from(value))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining(LanguageErrorCode.LANGUAGE_NOT_FOUND.getMessage());
    }

}