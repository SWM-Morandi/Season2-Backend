package kr.co.morandi.backend.docs.submit;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.docs.RestDocsSupport;
import kr.co.morandi.backend.judgement.application.usecase.submit.BaekjoonSubmitUsecase;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.judgement.infrastructure.controller.BaekjoonSubmitController;
import kr.co.morandi.backend.judgement.infrastructure.controller.request.BaekjoonJudgementRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BaekjoonSubmitControllerDocsTest extends RestDocsSupport {

    private final BaekjoonSubmitUsecase baekjoonSubmitUsecase = mock(BaekjoonSubmitUsecase.class);
    @Override
    protected Object initController() {
        return new BaekjoonSubmitController(baekjoonSubmitUsecase);
    }

    @DisplayName("백준 제출 API")
    @Test
    void submit() throws Exception {
        Long defenseSessionId = 1L;
        Long problemNumber = 1L;
        Language language = Language.JAVA;
        String sourceCode = "sourceCode";
        SubmitVisibility submitVisibility = SubmitVisibility.OPEN;
        BaekjoonJudgementRequest request = BaekjoonJudgementRequest.builder()
                .defenseSessionId(defenseSessionId)
                .problemNumber(problemNumber)
                .language(language)
                .sourceCode(sourceCode)
                .submitVisibility(submitVisibility)
                .build();

        doNothing().when(baekjoonSubmitUsecase).judgement(any());


        // when
        final ResultActions perform = mockMvc.perform(post("/submit")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        perform
                .andExpect(status().isOk())
                .andDo(document("submit-for-judgement",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("defenseSessionId").type(JsonFieldType.NUMBER)
                                        .description("디펜스 세션 아이디"),
                                fieldWithPath("problemNumber").type(JsonFieldType.NUMBER)
                                        .description("문제 번호"),
                                fieldWithPath("language").type(JsonFieldType.STRING)
                                        .description("사용 언어 [JAVA/CPP/PYTHON] "),
                                fieldWithPath("sourceCode").type(JsonFieldType.STRING)
                                        .description("제출 소스 코드"),
                                fieldWithPath("submitVisibility").type(JsonFieldType.STRING)
                                        .description("제출 공개 여부 [OPEN/CLOSE]")
                        )));
    }

}
