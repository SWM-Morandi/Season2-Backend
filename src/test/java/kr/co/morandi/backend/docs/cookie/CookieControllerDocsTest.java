package kr.co.morandi.backend.docs.cookie;

import kr.co.morandi.backend.docs.RestDocsSupport;
import kr.co.morandi.backend.judgement.application.service.baekjoon.cookie.BaekjoonMemberCookieService;
import kr.co.morandi.backend.judgement.infrastructure.controller.cookie.BaekjoonMemberCookieRequest;
import kr.co.morandi.backend.judgement.infrastructure.controller.cookie.CookieController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CookieControllerDocsTest extends RestDocsSupport {

    private final BaekjoonMemberCookieService cookieService = mock(BaekjoonMemberCookieService.class);
    @Override
    protected Object initController() {
        return new CookieController(cookieService);
    }

    @DisplayName("백준 쿠키를 저장하는 API")
    @Test
    void saveMemberBaekjoonCookie() throws Exception {
        BaekjoonMemberCookieRequest request = new BaekjoonMemberCookieRequest("cookie");

        doNothing().when(cookieService).saveMemberBaekjoonCookie(any());

        final ResultActions perform = mockMvc.perform(post("/cookie/baekjoon")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        perform
                .andExpect(status().isOk())
                .andDo(document("save-member-baekjoon-cookie",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint())));
    }
}
