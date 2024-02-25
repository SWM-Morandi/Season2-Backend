package kr.co.morandi.backend.domain.contentrecord.customdefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("CustomDefenseRecord")
public class CustomDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Long solvedCount;
    private Long problemCount;

    private CustomDefenseRecord(Long totalSolvedTime, Long solvedCount, Long problemCount) {
        this.totalSolvedTime = totalSolvedTime;
        this.solvedCount = solvedCount;
        this.problemCount = problemCount;
    }
}
