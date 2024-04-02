package kr.co.morandi.backend.defense_management.application.mapper.tempcode;

import kr.co.morandi.backend.defense_management.application.response.tempcode.TempCodeResponse;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
class TempCodeMapperTest {

    @DisplayName("아무 것도 포함하지 않고 생성하면 초기화된 TempCodeResponses를 반환한다.")
    @Test
    void createTempCodeResponses() {
        // given
        Set<TempCode> tempCodes = Set.of();

        // when
        final Set<TempCodeResponse> responses = TempCodeMapper.createTempCodeResponses(tempCodes);

        // then
        assertThat(responses).hasSize(3)
                .extracting("language", "code")
                .containsExactlyInAnyOrder(
                        tuple(JAVA, JAVA.getInitialCode()),
                        tuple(PYTHON, PYTHON.getInitialCode()),
                        tuple(CPP, CPP.getInitialCode())
                );

    }

    @DisplayName("일부 언어를 포함하고 나머지는 초기화된 TempCodeResponses를 반환한다.")
    @Test
    void createTempCodeResponsesWithSomeTempCodes() {
        //given
        Set<TempCode> tempCodes = Set.of(
                TempCode.builder()
                        .language(JAVA)
                        .code("java code")
                        .build(),
                TempCode.builder()
                        .language(PYTHON)
                        .code("python code")
                        .build()
        );

        // when
        final Set<TempCodeResponse> responses = TempCodeMapper.createTempCodeResponses(tempCodes);

        // then
        assertThat(responses).hasSize(3)
                .extracting("language", "code")
                .containsExactlyInAnyOrder(
                        tuple(JAVA, "java code"),
                        tuple(PYTHON, "python code"),
                        tuple(CPP, CPP.getInitialCode())
                );

    }

}