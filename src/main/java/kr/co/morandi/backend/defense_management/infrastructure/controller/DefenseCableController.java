package kr.co.morandi.backend.defense_management.infrastructure.controller;

import kr.co.morandi.backend.common.web.MemberId;
import kr.co.morandi.backend.defense_management.application.service.message.DefenseMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/defense")
@RequiredArgsConstructor
public class DefenseCableController {

    private final DefenseMessageService defenseMessageService;

    @GetMapping(value = "/{sessionId}/cable", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter defenseCable(@PathVariable Long sessionId, @MemberId Long memberId) {

        return defenseMessageService.getConnection(sessionId, memberId);
    }


}
