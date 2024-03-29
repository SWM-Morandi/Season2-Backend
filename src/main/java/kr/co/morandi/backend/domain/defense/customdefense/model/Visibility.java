package kr.co.morandi.backend.domain.defense.customdefense.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Visibility {
    OPEN(true),
    CLOSE(false);

    private final boolean isVisible;
}
