package kr.co.morandi.backend.domain.contentrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("StageDefenseRecord")
public class StageDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Long stageCount;

    private StageDefenseRecord(Long totalSolvedTime, Long stageCount) {
        this.totalSolvedTime = totalSolvedTime;
        this.stageCount = stageCount;
    }
}
