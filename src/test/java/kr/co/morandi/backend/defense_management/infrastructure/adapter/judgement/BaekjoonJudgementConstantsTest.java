package kr.co.morandi.backend.defense_management.infrastructure.adapter.judgement;

import kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit.BaekjoonSubmitLanguageCode;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BaekjoonJudgementConstantsTest {

    @DisplayName("BaekjoonJudgementConstants의 Language에 해당하는 아이디를 가져올 수 있다.")
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


}