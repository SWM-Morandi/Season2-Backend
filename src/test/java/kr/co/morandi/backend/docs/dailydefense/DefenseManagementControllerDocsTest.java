package kr.co.morandi.backend.docs.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_management.application.response.session.DefenseProblemResponse;
import kr.co.morandi.backend.defense_management.application.response.session.StartDailyDefenseResponse;
import kr.co.morandi.backend.defense_management.application.response.tempcode.TempCodeResponse;
import kr.co.morandi.backend.defense_management.application.usecase.session.DailyDefenseManagementUsecase;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_management.infrastructure.controller.DefenseMangementController;
import kr.co.morandi.backend.defense_management.infrastructure.request.dailydefense.StartDailyDefenseRequest;
import kr.co.morandi.backend.docs.RestDocsSupport;
import kr.co.morandi.backend.problem_information.application.response.problemcontent.ProblemContent;
import kr.co.morandi.backend.problem_information.application.response.problemcontent.SampleData;
import kr.co.morandi.backend.problem_information.application.response.problemcontent.Subtask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DefenseManagementControllerDocsTest extends RestDocsSupport {

    private final DailyDefenseManagementUsecase dailyDefenseManagementService = mock(DailyDefenseManagementUsecase.class);

    @Override
    protected Object initController() {
        return new DefenseMangementController(dailyDefenseManagementService);
    }

    @DisplayName("DailyDefense를 시작하는 API")
    @Test
    void getDailyDefenseInfo() throws Exception {
        Subtask subtask = Subtask.builder()
                .title("Basic Cases")
                .conditions(List.of("Input range 1-100", "No negative numbers"))
                .tableConditionsHtml("<table><tr><td>Condition</td></tr><tr><td>Input range 1-100</td></tr><tr><td>No negative numbers</td></tr></table>")
                .build();

        SampleData sampleData = SampleData.builder()
                .input("1 2")
                .output("3")
                .explanation("The output 3 is the sum of 1 and 2.")
                .build();

        ProblemContent problemContent = ProblemContent.builder()
                .baekjoonProblemId(1001L)
                .title("A+B Problem")
                .memoryLimit("128MB")
                .timeLimit("1s")
                .description("Calculate A + B.")
                .input("Two integers A and B.")
                .output("Output of A + B.")
                .samples(List.of(sampleData))
                .hint("Use simple addition.")
                .subtasks(List.of(subtask))
                .problemLimit("No specific limits.")
                .additionalTimeLimit("None")
                .additionalJudgeInfo("Standard problem.")
                .error(null)
                .build();

        TempCodeResponse java = TempCodeResponse.builder()
                .language(Language.JAVA)
                .code("public class Main { public static void main(String[] args) { System.out.println(1 + 1); } }")
                .build();

        TempCodeResponse cpp = TempCodeResponse.builder()
                .language(Language.CPP)
                .code("#include <iostream>\nint main() { std::cout << 1 + 1 << std::endl; return 0; }")
                .build();

        TempCodeResponse python = TempCodeResponse.builder()
                .language(Language.PYTHON)
                .code("print(1 + 1)")
                .build();

        DefenseProblemResponse defenseProblemResponse = DefenseProblemResponse.builder()
                .problemId(100L)
                .problemNumber(1L)
                .baekjoonProblemId(1001L)
                .content(problemContent)
                .isCorrect(true)
                .lastAccessLanguage(Language.JAVA)
                .tempCodes(Set.of(java, cpp, python))
                .build();
        final StartDailyDefenseResponse startDailyDefenseResponse = StartDailyDefenseResponse.builder()
                .defenseSessionId(101L)
                .contentName("Daily Challenge")
                .defenseType(DefenseType.DAILY)
                .lastAccessTime(LocalDateTime.of(2021, 4, 27, 0, 0, 0))
                .defenseProblems(List.of(defenseProblemResponse))
                .build();

        when(dailyDefenseManagementService.startDailyDefense(any(), any(), any()))
                .thenReturn(startDailyDefenseResponse);

        final StartDailyDefenseRequest request = StartDailyDefenseRequest.builder()
                .problemNumber(1L)
                .build();

        final ResultActions perform = mockMvc.perform(post("/daily-defense")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        perform
                .andExpect(status().isOk())
                .andDo(document("daily-defense-start",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                // StartDailyDefenseResponse
                                fieldWithPath("defenseSessionId").type(JsonFieldType.NUMBER)
                                        .description("오늘의 문제 세션의 고유 ID"),
                                fieldWithPath("contentName").type(JsonFieldType.STRING)
                                        .description("오늘의 문제 이름"),
                                fieldWithPath("defenseType").type(JsonFieldType.STRING)
                                        .description("디펜스 유형"),
                                fieldWithPath("lastAccessTime").type(JsonFieldType.STRING)
                                        .description("마지막으로 콘텐츠에 접근한 시간"),
                                fieldWithPath("defenseProblems").type(JsonFieldType.ARRAY)
                                        .description("오늘의 문제의 문제 목록[]"),

                                // DefenseProblemResponse - Array Element
                                fieldWithPath("defenseProblems[].problemId").type(JsonFieldType.NUMBER)
                                        .description("문제의 고유 ID"),
                                fieldWithPath("defenseProblems[].problemNumber").type(JsonFieldType.NUMBER)
                                        .description("문제 번호"),
                                fieldWithPath("defenseProblems[].baekjoonProblemId").type(JsonFieldType.NUMBER)
                                        .description("백준 문제 ID"),
                                fieldWithPath("defenseProblems[].content").type(JsonFieldType.OBJECT)
                                        .description("문제의 내용 상세"),
                                fieldWithPath("defenseProblems[].isCorrect").type(JsonFieldType.BOOLEAN)
                                        .description("문제가 올바르게 해결되었는지 여부"),
                                fieldWithPath("defenseProblems[].lastAccessLanguage").type(JsonFieldType.STRING)
                                        .description("사용된 마지막 프로그래밍 언어"),
                                fieldWithPath("defenseProblems[].tempCodes").type(JsonFieldType.ARRAY)
                                        .description("문제에 대해 작성된 임시 코드[]"),

                                // ProblemContent within DefenseProblemResponse
                                fieldWithPath("defenseProblems[].content.baekjoonProblemId").type(JsonFieldType.NUMBER)
                                        .description("백준 문제 ID"),
                                fieldWithPath("defenseProblems[].content.title").type(JsonFieldType.STRING)
                                        .description("문제의 제목"),
                                fieldWithPath("defenseProblems[].content.memoryLimit").type(JsonFieldType.STRING)
                                        .description("문제의 메모리 제한"),
                                fieldWithPath("defenseProblems[].content.timeLimit").type(JsonFieldType.STRING)
                                        .description("문제의 시간 제한"),
                                fieldWithPath("defenseProblems[].content.description").type(JsonFieldType.STRING)
                                        .description("문제의 설명"),
                                fieldWithPath("defenseProblems[].content.input").type(JsonFieldType.STRING)
                                        .description("문제의 입력 형식"),
                                fieldWithPath("defenseProblems[].content.output").type(JsonFieldType.STRING)
                                        .description("문제의 출력 형식"),
                                fieldWithPath("defenseProblems[].content.samples").type(JsonFieldType.ARRAY)
                                        .description("문제의 샘플 입력/출력 값 배열"),
                                fieldWithPath("defenseProblems[].content.samples[].input").type(JsonFieldType.STRING)
                                        .description("문제의 샘플 입력값"),
                                fieldWithPath("defenseProblems[].content.samples[].output").type(JsonFieldType.STRING)
                                        .description("문제의 샘플 출력값"),
                                fieldWithPath("defenseProblems[].content.samples[].explanation").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("입출력 예제에 대한 설명"),
                                fieldWithPath("defenseProblems[].content.hint").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("문제를 해결하기 위한 힌트"),
                                fieldWithPath("defenseProblems[].content.subtasks").type(JsonFieldType.ARRAY)
                                        .description("서브테스크 목록"),
                                fieldWithPath("defenseProblems[].content.subtasks[].title").type(JsonFieldType.STRING)
                                        .description("부분 작업의 제목"),
                                fieldWithPath("defenseProblems[].content.subtasks[].conditions").type(JsonFieldType.ARRAY)
                                        .description("부분 작업의 조건 목록 (일반적으로 conditions 와 tableConditionsHtml 중 1개가 주어짐)"),
                                fieldWithPath("defenseProblems[].content.subtasks[].tableConditionsHtml").type(JsonFieldType.STRING)
                                        .description("HTML 형식의 조건 표"),
                                fieldWithPath("defenseProblems[].content.problemLimit").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("문제 제한"),
                                fieldWithPath("defenseProblems[].content.additionalTimeLimit").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("추가 시간 제한"),
                                fieldWithPath("defenseProblems[].content.additionalJudgeInfo").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("추가 채점 정보"),
                                fieldWithPath("defenseProblems[].content.error").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("문제 발생 시 반환되는 오류 필드만 반환됨"),

                                // TempCodeResponse within DefenseProblemResponse
                                fieldWithPath("defenseProblems[].tempCodes[].language").type(JsonFieldType.STRING)
                                        .description("임시 저장된 코드의 프로그래밍 언어"),
                                fieldWithPath("defenseProblems[].tempCodes[].code").type(JsonFieldType.STRING)
                                        .description("임시 저장된 코드 스니펫")
                        )
                ));

    }
}
