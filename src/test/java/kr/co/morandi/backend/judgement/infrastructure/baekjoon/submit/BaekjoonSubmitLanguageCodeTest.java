package kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaekjoonSubmitLanguageCodeTest {

    @DisplayName("BaekjoonJudgementConstants의 CPP에 해당하는 아이디를 가져올 수 있다.")
    @Test
    void getLanguageId() {
        // given
        Language language = Language.CPP;

        // when
        String languageId = BaekjoonSubmitLanguageCode.getLanguageCode(language);

        // then
        assertThat(languageId)
                .isEqualTo("84");

    }

    @DisplayName("BaekjoonJudgementConstants의 JAVA 해당하는 아이디를 가져올 수 있다.")
    @Test
    void getLanguageId2() {
        // given
        Language language = Language.JAVA;

        // when
        String languageId = BaekjoonSubmitLanguageCode.getLanguageCode(language);

        // then
        assertThat(languageId)
                .isEqualTo("93");

    }

    @DisplayName("BaekjoonJudgementConstants의 PYTHON에 해당하는 아이디를 가져올 수 있다.")
    @Test
    void getLanguageId3() {
        // given
        Language language = Language.PYTHON;

        // when
        String languageId = BaekjoonSubmitLanguageCode.getLanguageCode(language);

        // then
        assertThat(languageId)
                .isEqualTo("28");

    }



}