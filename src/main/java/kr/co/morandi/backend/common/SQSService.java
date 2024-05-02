package kr.co.morandi.backend.common;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SQSService {

    @Value("${cloud.aws.sqs.queue.compile-url}")
    private String url;

    private final AmazonSQS amazonSQS;

    private final ObjectMapper objectMapper;
    public SendMessageResult sendMessage(CodeRequest codeRequest) throws JsonProcessingException {
        String requestString = objectMapper.writeValueAsString(codeRequest);
        SendMessageRequest sendMessageRequest = new SendMessageRequest(url, requestString);
        return amazonSQS.sendMessage(sendMessageRequest);
    }
}
