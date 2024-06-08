package kr.co.morandi.backend.judgement.domain.event;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record TempCodeSaveEvent(
    Long defenseSessionId,
    Long problemNumber,
    String sourceCode,
    Language language
) {}
