package kr.co.morandi.backend.defense_management.infrastructure.controller;

import kr.co.morandi.backend.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SessionConnectionControllerTest extends ControllerTestSupport {

    @DisplayName("[GET] 메세지를 받기 위한 SSE를 연결한다.")
    @WithMockUser
    @Test
    void connectSession() throws Exception {
        Long 세션_아이디 = 1L;

        SseEmitter expectedEmitter = new SseEmitter();
        expectedEmitter.send("test");

        when(defenseMessageService.getConnection(anyLong(), anyLong()))
                    .thenReturn(expectedEmitter);

        mockMvc.perform(get("/session/{sessionId}/connect", 세션_아이디)
                        .accept(MediaType.TEXT_EVENT_STREAM))
                .andExpect(status().isOk());
    }

}