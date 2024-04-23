package kr.co.morandi.backend.defense_record.infrastructure.controller;

import kr.co.morandi.backend.defense_record.application.dto.DailyDefenseRankPageResponse;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import kr.co.morandi.backend.member_management.infrastructure.config.cookie.utils.CookieUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DailyRecordController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {OncePerRequestFilter.class})})
@ActiveProfiles("test")
class DailyRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DailyRecordRankUseCase dailyRecordRankUseCase;

    @MockBean
    private CookieUtils cookieUtils;

    @DisplayName("[GET] DailyDefense 순위를 조회한다.")
    @Test
    void getDailyRecordRank() throws Exception {
        // given
        int page = 0;
        int size = 5;
        when(dailyRecordRankUseCase.getDailyRecordRank(any(), anyInt(), anyInt()))
                .thenReturn(DailyDefenseRankPageResponse.builder()
                        .totalPage(1)
                        .currentPage(0)
                        .dailyRecords(List.of())
                        .build());


        // when
        ResultActions perform = mockMvc.perform(
                get("/daily-record/rankings")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)
                        ));


        // then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalPage").isNumber())
            .andExpect(jsonPath("$.currentPage").isNumber())
            .andExpect(jsonPath("$.dailyRecords").isArray());
    }


}