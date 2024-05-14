package kr.co.morandi.backend.defense_management.application.service.codesubmit;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_management.infrastructure.request.codesubmit.CodeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SQSServiceTest extends IntegrationTestSupport {
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
    public void sendMessage() throws JsonProcessingException {
        CodeRequest 코드_요청_정보 = new CodeRequest();
        String JSON_메시지 = "{\"key\": \"value\"}";
        when(objectMapper.writeValueAsString(any(CodeRequest.class))).thenReturn(JSON_메시지);
        SendMessageResult 전송_결과물 = new SendMessageResult().withMessageId("12345");
        when(amazonSQS.sendMessage(any(SendMessageRequest.class))).thenReturn(전송_결과물);

        // When
        SendMessageResult 실제_전송_결과물 = sqsService.sendMessage(코드_요청_정보);

        // Then
        verify(objectMapper).writeValueAsString(코드_요청_정보);
        verify(amazonSQS).sendMessage(new SendMessageRequest(url, JSON_메시지));
        assertEquals("12345", 실제_전송_결과물.getMessageId());
    }
}