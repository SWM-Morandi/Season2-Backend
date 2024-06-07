package kr.co.morandi.backend.judgement.domain.event;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TempCodeSaveEvent {

    private final Long defenseSessionId;
    private final Long problemNumber;
    private final String sourceCode;
    private final Language language;
}
