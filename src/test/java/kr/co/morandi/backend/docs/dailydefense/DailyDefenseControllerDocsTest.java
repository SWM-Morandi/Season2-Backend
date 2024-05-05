package kr.co.morandi.backend.docs.dailydefense;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseProblemInfoResponse;
import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import kr.co.morandi.backend.defense_information.infrastructure.controller.DailyDefenseController;
import kr.co.morandi.backend.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.S5;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DailyDefenseControllerDocsTest extends RestDocsSupport {

    private final DailyDefenseUseCase dailyDefenseUseCase = mock(DailyDefenseUseCase.class);
    @Override
    protected Object initController() {
        return new DailyDefenseController(dailyDefenseUseCase);
    }

    @DisplayName("DailyDefense 정보를 가져오는 API")
    @Test
    void getDailyDefenseInfo() throws Exception {

        final DailyDefenseProblemInfoResponse problem = DailyDefenseProblemInfoResponse.builder()
                .problemNumber(1L)
                .problemId(1L)
                .baekjoonProblemId(1000L)
                .difficulty(S5)
                .solvedCount(1L)
                .submitCount(1L)
                .isSolved(true)
                .build();

        when(dailyDefenseUseCase.getDailyDefenseInfo(any(), any()))
                .thenReturn(DailyDefenseInfoResponse.builder()
                        .problems(List.of(problem))
                        .defenseName("test")
                        .attemptCount(1L)
                        .problemCount(5)
                        .build());

        final ResultActions perform = mockMvc.perform(get("/daily-defense"));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problems").isArray())
                .andExpect(jsonPath("$.defenseName").isString())
                .andExpect(jsonPath("$.attemptCount").isNumber())
                .andExpect(jsonPath("$.problemCount").isNumber())
                .andDo(document("daily-defense-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("defenseName").type(JsonFieldType.STRING)
                                        .description("디펜스 이름"),
                                fieldWithPath("problemCount").type(JsonFieldType.NUMBER)
                                        .description("총 문제 수"),
                                fieldWithPath("attemptCount").type(JsonFieldType.NUMBER)
                                        .description("디펜스 시도 횟수"),
                                fieldWithPath("problems").type(JsonFieldType.ARRAY)
                                        .description("디펜스 문제 목록"),
                                fieldWithPath("problems[].problemNumber").type(JsonFieldType.NUMBER)
                                        .description("시도하는 문제 번호"),
                                fieldWithPath("problems[].problemId").type(JsonFieldType.NUMBER)
                                        .description("시도하는 문제의 PK"),
                                fieldWithPath("problems[].baekjoonProblemId").type(JsonFieldType.NUMBER)
                                        .description("백준 문제 번호"),
                                fieldWithPath("problems[].difficulty").type(JsonFieldType.STRING)
                                        .description("문제 난이도 ex) SILVER"),
                                fieldWithPath("problems[].solvedCount").type(JsonFieldType.NUMBER)
                                        .description("정답자 수"),
                                fieldWithPath("problems[].submitCount").type(JsonFieldType.NUMBER)
                                        .description("제출한 사람 수"),
                                fieldWithPath("problems[].isSolved").type(JsonFieldType.BOOLEAN)
                                        .optional()
                                        .description("해당 사용자가 정답을 맞췄는지 여부, 이 필드가 없으면 아직 시도하지 않은 문제")
                        )
                ));

    }
}
