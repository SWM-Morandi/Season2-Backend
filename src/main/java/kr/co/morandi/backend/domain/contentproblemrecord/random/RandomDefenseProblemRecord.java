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
@DiscriminatorValue("RandomDefenseProblemRecord")
public class RandomDefenseProblemRecord extends ContentProblemRecord {
    private Long solvedTime;

    private RandomDefenseProblemRecord(Long solvedTime) {
        this.solvedTime = solvedTime;
    }
}
