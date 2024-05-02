package kr.co.morandi.backend.common;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MailController {

    private final SQSService sqsService;

    private final SseEmitterService sseEmitterService;
    @PostMapping("/send")
    public String send(@RequestBody CodeRequest codeRequest) throws JsonProcessingException {
        SendMessageResult sendMessageResult = sqsService.sendMessage(codeRequest);
        return sendMessageResult.getMessageId();
    }
    @GetMapping("/sse")
    public SseEmitter sse(String messageId)
    {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
//        emitter.onCompletion(() -> sseEmitterService.removeEmitter(messageId));
//        emitter.onTimeout(() -> sseEmitterService.removeEmitter(messageId));
        sseEmitterService.addEmitter(messageId, emitter);
        return emitter;
    }
}
