package kr.co.morandi.backend.domain.defense.model.customdefense;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Visibility {
    OPEN(true),
    CLOSE(false);

    private final boolean isVisible;
}
