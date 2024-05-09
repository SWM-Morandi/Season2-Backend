package kr.co.morandi.backend.defense_management.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateDefenseMessageEvent {

    private final Long defenseSessionId;
}
