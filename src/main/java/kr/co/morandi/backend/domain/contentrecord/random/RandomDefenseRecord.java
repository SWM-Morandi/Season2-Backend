package kr.co.morandi.backend.domain.contentrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("RandomDefenseRecord")
public class RandomDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Long solvedCount;
    private Long problemCount;

    private RandomDefenseRecord(Long totalSolvedTime, Long solvedCount, Long problemCount) {
        this.totalSolvedTime = totalSolvedTime;
        this.solvedCount = solvedCount;
        this.problemCount = problemCount;
    }
}
