package kr.co.morandi.backend.docs.dailydefense;

import kr.co.morandi.backend.defense_management.application.service.message.DefenseMessageService;
import kr.co.morandi.backend.defense_management.infrastructure.controller.SessionConnectionController;
import kr.co.morandi.backend.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class SessionConnectionControllerDocsTest extends RestDocsSupport {

    private final DefenseMessageService defenseMessageService = mock(DefenseMessageService.class);
    @Override
    protected Object initController() {
        return new SessionConnectionController(defenseMessageService);
    }

    @DisplayName("SSE 세션을 연결하는 API")
    @Test
    void connectSession() throws Exception {
        Long 세션_아이디 = 1L;

        SseEmitter expectedEmitter = new SseEmitter();

        when(defenseMessageService.getConnection(anyLong(), anyLong()))
                .thenReturn(expectedEmitter);


        mockMvc.perform(get("/session/{sessionId}/connect", 세션_아이디)
                        .accept(MediaType.TEXT_EVENT_STREAM))
                .andDo(document("connect-session",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                       ));
    }
}
