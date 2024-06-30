package kr.co.morandi.backend.judgement.infrastructure.controller.cookie;

import kr.co.morandi.backend.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CookieControllerTest extends ControllerTestSupport {

    @DisplayName("백준 쿠키 정보를 저장할 수 있다.")
    @Test
    void saveMemberBaekjoonCookie() throws Exception {
        // given
        String 쿠키 = "dummycookie";
        BaekjoonMemberCookieRequest request = new BaekjoonMemberCookieRequest(쿠키);
        doNothing().when(baekjoonMemberCookieService).saveMemberBaekjoonCookie(any());


        // when
        final ResultActions perform = mockMvc.perform(post("/cookie/baekjoon")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        perform
                .andExpect(status().isOk());

    }

    @DisplayName("cokkie 값이 빈 값일 경우 예외를 반환한다.")
    @Test
    void saveMemberBaekjoonCookieWithEmptyCookie() throws Exception {
        // given
        String 쿠키 = null;
        BaekjoonMemberCookieRequest request = new BaekjoonMemberCookieRequest(쿠키);
        doNothing().when(baekjoonMemberCookieService).saveMemberBaekjoonCookie(any());


        // when
        final ResultActions perform = mockMvc.perform(post("/cookie/baekjoon")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)));

        // then
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.message").value("cookie: Cookie 값은 비어 있을 수 없습니다."));

    }

}