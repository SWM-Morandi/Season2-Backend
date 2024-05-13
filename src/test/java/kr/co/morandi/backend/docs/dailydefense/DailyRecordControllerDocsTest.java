package kr.co.morandi.backend.docs.dailydefense;

import kr.co.morandi.backend.defense_record.application.dto.DailyDefenseRankPageResponse;
import kr.co.morandi.backend.defense_record.application.dto.DailyDetailRankResponse;
import kr.co.morandi.backend.defense_record.application.dto.DailyRecordRankResponse;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import kr.co.morandi.backend.defense_record.application.util.TimeFormatHelper;
import kr.co.morandi.backend.defense_record.infrastructure.controller.DailyRecordController;
import kr.co.morandi.backend.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DailyRecordControllerDocsTest extends RestDocsSupport {

    private final DailyRecordRankUseCase dailyRecordRankUseCase = mock(DailyRecordRankUseCase.class);

    @Override
    protected Object initController() {
        return new DailyRecordController(dailyRecordRankUseCase);
    }

    @DisplayName("오늘의 문제 랭킹 반환 API")
    @Test
    void getDailyRecordRank() throws Exception {
        // 5개 문제에 대한 세부 정보 생성
        Map<Long, DailyDetailRankResponse> allDetails = new HashMap<>();
        for (long i = 1; i <= 5; i++) {  // 5개의 문제
            allDetails.put(i, DailyDetailRankResponse.builder()
                    .problemNumber(i)
                    .isSolved(i % 2 == 0)  // 홀수 문제는 해결하지 못함, 짝수 문제는 해결함
                    .solvedTime(TimeFormatHelper.solvedTimeToString(i * 60000))  // 문제별로 1, 2, 3, 4, 5분 소요
                    .build());
        }


        // 각 유저가 5개의 문제에 대한 세부 정보를 갖도록 설정
        List<DailyRecordRankResponse> records = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {  // 5명의 유저
            List<DailyDetailRankResponse> details = allDetails.values().stream()
                    .collect(Collectors.toList());

            records.add(DailyRecordRankResponse.builder()
                    .nickname("test" + i)
                    .rank(i)
                    .solvedCount(details.stream().filter(DailyDetailRankResponse::getIsSolved).count())  // 해결한 문제 수
                    .updatedAt(LocalDateTime.now())
                    .totalSolvedTime(TimeFormatHelper.solvedTimeToString(details.stream()
                            .mapToLong(detail -> TimeFormatHelper.stringToSolvedTime(detail.getSolvedTime()))
                            .sum()))
                    .rankDetails(details)
                    .build());

        }

        DailyDefenseRankPageResponse response = DailyDefenseRankPageResponse.builder()
                .dailyRecords(records)
                .totalPage(1)
                .currentPage(0)
                .build();

        when(dailyRecordRankUseCase.getDailyRecordRank(any(), anyInt(), anyInt()))
                .thenReturn(response);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("page", "0");
            params.add("size", "5");


        final ResultActions perform = mockMvc.perform(get("/daily-record/rankings")
                .params(params));

        perform
                .andExpect(status().isOk())
                .andDo(document("daily-defense-ranking",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("dailyRecords")
                                        .type(JsonFieldType.ARRAY)
                                        .description("오늘의 문제 순위 기록 목록"),
                                fieldWithPath("dailyRecords[].nickname")
                                        .type(JsonFieldType.STRING)
                                        .description("사용자 닉네임"),
                                fieldWithPath("dailyRecords[].rank")
                                        .type(JsonFieldType.NUMBER)
                                        .description("사용자의 순위"),
                                fieldWithPath("dailyRecords[].solvedCount")
                                        .type(JsonFieldType.NUMBER)
                                        .description("해결한 문제 수"),
                                fieldWithPath("dailyRecords[].updatedAt")
                                        .type(JsonFieldType.STRING)
                                        .description("최근 업데이트 시간"),
                                fieldWithPath("dailyRecords[].totalSolvedTime")
                                        .type(JsonFieldType.STRING)
                                        .description("총 해결 시간"),
                                fieldWithPath("dailyRecords[].rankDetails")
                                        .type(JsonFieldType.ARRAY)
                                        .description("문제별 세부 순위 정보"),
                                fieldWithPath("dailyRecords[].rankDetails[].problemNumber")
                                        .type(JsonFieldType.NUMBER)
                                        .description("문제 번호"),
                                fieldWithPath("dailyRecords[].rankDetails[].isSolved")
                                        .type(JsonFieldType.BOOLEAN)
                                        .description("해결 여부"),
                                fieldWithPath("dailyRecords[].rankDetails[].solvedTime")
                                        .type(JsonFieldType.STRING)
                                        .description("해결 시간"),
                                fieldWithPath("totalPage")
                                        .type(JsonFieldType.NUMBER)
                                        .description("전체 페이지 수"),
                                fieldWithPath("currentPage")
                                        .type(JsonFieldType.NUMBER)
                                        .description("현재 페이지 번호")
                        )));


    }
}
