package kr.co.morandi.backend.defense_management.application.service.codesubmit;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.infrastructure.request.codesubmit.CodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SQSServiceTest {
    @Mock
    private AmazonSQS amazonSQS;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private SQSService sqsService;

    @Value("${cloud.aws.sqs.queue.compile-url}")
    private String url;
    @DisplayName("사용자가 소스코드를 제출하면 AWS SQS에 JSON 메시지가 올바른 주소에 전송된다.")
    @Test
    void correctSendMessage() throws JsonProcessingException {
        CodeRequest 코드_요청_정보 = new CodeRequest();
        String JSON_메시지 = "{\"key\": \"value\"}";
        when(objectMapper.writeValueAsString(any(CodeRequest.class))).thenReturn(JSON_메시지);
        SendMessageResult 전송_결과물 = new SendMessageResult().withMessageId("12345");
        when(amazonSQS.sendMessage(any(SendMessageRequest.class))).thenReturn(전송_결과물);

        // When
        SendMessageResult 실제_전송_결과물 = sqsService.sendMessage(코드_요청_정보);

        // Then
//        verify(objectMapper).writeValueAsString(CodeRequest.class);
        verify(amazonSQS).sendMessage(new SendMessageRequest(url, JSON_메시지));
        assertEquals("12345", 실제_전송_결과물.getMessageId());
    }
    @DisplayName("사용자가 적절하지 않은 소스코드를 보내면 예외가 발생해야 한다.")
    @Test
    void incorrectSendMessage() throws JsonProcessingException {
        // Given
        CodeRequest 코드_요청_정보 = new CodeRequest();
        when(objectMapper.writeValueAsString(any(CodeRequest.class)))
                .thenThrow(new JsonProcessingException("Failed to convert object to JSON string") {});

        // When & Then
        assertThrows(MorandiException.class, () -> {
            sqsService.sendMessage(코드_요청_정보);
        });
        verify(amazonSQS, never()).sendMessage(any(SendMessageRequest.class));
    }

}