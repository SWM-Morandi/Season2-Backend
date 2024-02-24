package kr.co.morandi.backend.domain.contenttype.customdefense;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefenseTier {
    BRONZE(1),
    SILVER(2),
    GOLD(3),
    PLATINUM(4),
    DIAMOND(5),
    RUBY(6);
    private final int tier;
}
