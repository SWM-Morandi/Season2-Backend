package kr.co.morandi.backend.domain.contentproblemrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("StageDefenseProblemRecord")
public class StageDefenseProblemRecord extends ContentProblemRecord {
    private Long solvedTime;
    private Long stageNumber;

    private StageDefenseProblemRecord(Long solvedTime, Long stageNumber) {
        this.solvedTime = solvedTime;
        this.stageNumber = stageNumber;
    }
}
