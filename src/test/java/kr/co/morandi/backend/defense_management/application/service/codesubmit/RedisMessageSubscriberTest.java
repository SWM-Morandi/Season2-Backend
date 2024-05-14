package kr.co.morandi.backend.defense_management.application.service.codesubmit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.application.port.out.defensemessaging.DefenseMessagePort;
import kr.co.morandi.backend.defense_management.application.response.codesubmit.CodeResponse;
import kr.co.morandi.backend.defense_management.application.response.codesubmit.MessageResponse;
import kr.co.morandi.backend.defense_management.infrastructure.exception.RedisMessageErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
class RedisMessageSubscriberTest extends IntegrationTestSupport {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private DefenseMessagePort defenseMessagePort;
    @InjectMocks
    private RedisMessageSubscriber redisMessageSubscriber;
    @DisplayName("Redis pub/sub에 메시지가 도착하면 메시지가 정상적으로 SSE에 전송되어야 한다.")
    @Test
    void correctOnMessage() throws JsonProcessingException {
        // given
        String json = "{\"sseId\":\"123\", \"result\":\"Hello\"}";
        String channel = "channel";
        Message message = new DefaultMessage(channel.getBytes(), json.getBytes());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSseId("123");
        messageResponse.setResult("Hello");

        when(objectMapper.readValue(json, MessageResponse.class)).thenReturn(messageResponse);

        // when
        redisMessageSubscriber.onMessage(message, null);

        // then
        verify(objectMapper).readValue(json, MessageResponse.class);
        verify(defenseMessagePort).sendMessage(eq(123L), any(CodeResponse.class));
    }

    @DisplayName("Redis pub/sub에 형식에 맞지 않는 메시지가 오면 예외를 발생시켜야 한다.")
    @Test
    void incorrectOnMessage() throws JsonProcessingException {
        // given
        String incorrectJson = "{sseId:\"123\", result:\"Hello\"}";
        String channel = "channel";
        Message message = new DefaultMessage(channel.getBytes(), incorrectJson.getBytes());
        when(objectMapper.readValue(incorrectJson, MessageResponse.class)).thenThrow(new JsonProcessingException("Parse error") {});

        // when & then
        MorandiException morandiException = assertThrows(MorandiException.class, () -> redisMessageSubscriber.onMessage(message, null));
        assertEquals(RedisMessageErrorCode.MESSAGE_PARSE_ERROR, morandiException.getErrorCode());
        assertEquals("Redis의 메시지를 파싱하지 못했습니다.", morandiException.getMessage());
    }
}