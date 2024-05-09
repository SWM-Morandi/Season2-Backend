package kr.co.morandi.backend.defense_management.application.port.out.defensemessaging;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface DefenseMessagePort {

    void createConnection(Long defenseSessionId);
    SseEmitter getConnection(Long defenseSessionId);
    boolean sendMessage(Long defenseSessionId, String message);
}
