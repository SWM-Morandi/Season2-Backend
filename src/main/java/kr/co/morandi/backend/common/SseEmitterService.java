package kr.co.morandi.backend.common;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitterService {
    private final Map<Object, SseEmitter> emitters = new ConcurrentHashMap<>();
    public void addEmitter(String messageId, SseEmitter emitter) {
        emitters.put(messageId, emitter);
    }
    public void removeEmitter(String messageId) {
        emitters.remove(messageId.trim());
    }
    public void sendToEmitter(String messageId, Object data) {
        SseEmitter emitter = emitters.get(messageId);
        if (emitter != null) {
            try {
                emitter.send(data);
            } catch (Exception e) {
                System.out.println("Error sending SSE data: " + e.getMessage());
                removeEmitter(messageId); // Optionally remove on error
            }
        } else {
            System.out.println("No emitter found for messageId: " + messageId);
            System.out.println("Current emitters keys: " + emitters.keySet()); // 현재 등록된 키들 출력
        }
    }
}