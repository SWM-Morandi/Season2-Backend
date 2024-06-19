package kr.co.morandi.backend.judgement.infrastructure.controller;

import kr.co.morandi.backend.ControllerTestSupport;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.judgement.infrastructure.controller.request.BaekjoonJudgementRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BaekjoonSubmitControllerTest extends ControllerTestSupport {

    @DisplayName("제출 요청을 받아 처리한다.")
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
                .andExpect(status().isOk());
    }

    @DisplayName("defenseSessionId가 null일 경우 처리한다.")
    @Test
    void submitWhenDefenseSessionIdIsNull() throws Exception {

        Long defenseSessionId = null;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("defenseSessionId: defenseSessionId가 존재해야 합니다."));

    }

    @DisplayName("defenseSessionId가 음수일 경우 처리한다.")
    @Test
    void submitWhenDefenseSessionIdIsNegative() throws Exception {

        Long defenseSessionId = -1L;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("defenseSessionId: defenseSessionId는 양수여야 합니다."));

    }

    @DisplayName("problemNumber가 null일 경우 처리한다.")
    @Test
    void submitWhenProblemNumberIsNull() throws Exception {
        // when
        Long defenseSessionId = 1L;
        Long problemNumber = null;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("problemNumber: problemNumber가 존재해야 합니다."));

    }

    @DisplayName("problemNumber가 음수일 경우 처리한다.")
    @Test
    void submitWhenProblemNumberIsNegative() throws Exception {
        // when
        Long defenseSessionId = 1L;
        Long problemNumber = -1L;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("problemNumber: problemNumber가 양수여야 합니다."));

    }


    @DisplayName("language가 null일 경우 처리한다.")
    @Test
    void submitWhenLanguageIsNull() throws Exception {
        // when
        Long defenseSessionId = 1L;
        Long problemNumber = 1L;
        Language language = null;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("language: language가 존재해야 합니다."));

    }

    @DisplayName("sourceCode가 null일 경우 처리한다.")
    @Test
    void submitWhenSourceCodeIsNull() throws Exception {
        // when
        Long defenseSessionId = 1L;
        Long problemNumber = 1L;
        Language language = Language.JAVA;
        String sourceCode = null;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("sourceCode: sourceCode가 존재해야 합니다."));

    }

    @DisplayName("sourceCode가 비어있을 경우 처리한다.")
    @Test
    void submitWhenSourceCodeIsEmpty() throws Exception {
        // when
        Long defenseSessionId = 1L;
        Long problemNumber = 1L;
        Language language = Language.JAVA;
        String sourceCode = "";
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("sourceCode: sourceCode가 존재해야 합니다."));

    }

    @DisplayName("submitVisibility가 null일 경우 처리한다.")
    @Test
    void submitWhenSubmitVisibilityIsNull() throws Exception {
        // when
        Long defenseSessionId = 1L;
        Long problemNumber = 1L;
        Language language = Language.JAVA;
        String sourceCode = "sourceCode";
        SubmitVisibility submitVisibility = null;
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("submitVisibility: submitVisibility가 존재해야 합니다."));

    }
}