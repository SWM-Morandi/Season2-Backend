package kr.co.morandi.backend.domain.contenttype.randomcriteria;

import jakarta.persistence.Embeddable;
import kr.co.morandi.backend.domain.contenttype.tier.ProblemTier;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DifficultyRange {
    private ProblemTier startDifficulty;
    private ProblemTier endDifficulty;

    @Builder
    private DifficultyRange(ProblemTier startDifficulty, ProblemTier endDifficulty) {
        this.startDifficulty = startDifficulty;
        this.endDifficulty = endDifficulty;
    }

    public static DifficultyRange of(ProblemTier startDifficulty, ProblemTier endDifficulty) {
        if(startDifficulty == null || endDifficulty == null)
            throw new IllegalArgumentException("DifficultyRange must not be null");
        if(startDifficulty.compareTo(endDifficulty) > 0)
            throw new IllegalArgumentException("Start difficulty must be less than or equal to end difficulty");
        if(startDifficulty.equals(endDifficulty))
            throw new IllegalArgumentException("Start difficulty and end difficulty must not be same");

        return DifficultyRange.builder()
                .startDifficulty(startDifficulty)
                .endDifficulty(endDifficulty)
                .build();
    }
}
