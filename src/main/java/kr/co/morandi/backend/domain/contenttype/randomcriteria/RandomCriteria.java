package kr.co.morandi.backend.domain.contenttype.randomcriteria;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomCriteria {

    @Embedded
    private DifficultyRange difficultyRange;

    private Long minSolvedCount;
    private Long maxSolvedCount;

    @Builder
    private RandomCriteria(DifficultyRange difficultyRange, Long minSolvedCount, Long maxSolvedCount) {
        this.difficultyRange = difficultyRange;
        this.minSolvedCount = minSolvedCount;
        this.maxSolvedCount = maxSolvedCount;
    }

    public static RandomCriteria of(DifficultyRange difficultyRange, Long minSolvedCount, Long maxSolvedCount) {
        if(minSolvedCount == null || maxSolvedCount == null)
            throw new IllegalArgumentException("Solved count must not be null");
        if(minSolvedCount < 0 || maxSolvedCount < 0)
            throw new IllegalArgumentException("Solved count must be greater than or equal to 0");
        if(minSolvedCount >= maxSolvedCount)
            throw new IllegalArgumentException("Min solved count must be less than or equal to max solved count");


        return RandomCriteria.builder()
                .difficultyRange(difficultyRange)
                .minSolvedCount(minSolvedCount)
                .maxSolvedCount(maxSolvedCount)
                .build();
    }
}
