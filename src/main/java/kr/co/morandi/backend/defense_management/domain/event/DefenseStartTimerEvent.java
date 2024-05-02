package kr.co.morandi.backend.defense_management.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DefenseStartTimerEvent {

    private final Long sessionId;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
}
