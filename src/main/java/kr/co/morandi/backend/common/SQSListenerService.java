package kr.co.morandi.backend.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class SQSListenerService {

    private final SseEmitterService sseEmitterService;

    private final ObjectMapper objectMapper;
    @SqsListener("CodeResponseQueue")
    public void handle(String codeResponse) {

        // messageId에 해당하는 codeResponse 저장

        try {
            JsonNode jsonNode = objectMapper.readTree(codeResponse);
            String messageId = jsonNode.get("message_id").asText();
            sseEmitterService.sendToEmitter(messageId, codeResponse);
        } catch (JsonProcessingException e) {
            System.err.println("Error");
        }
    }
}
