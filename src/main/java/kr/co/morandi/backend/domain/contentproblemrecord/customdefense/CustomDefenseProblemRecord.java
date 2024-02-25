package kr.co.morandi.backend.domain.contentproblemrecord.customdefense;

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
@DiscriminatorValue("CustomDefenseProblemRecord")
public class CustomDefenseProblemRecord extends ContentProblemRecord {
    private Long solvedTime;

    private CustomDefenseProblemRecord(Long solvedTime) {
        this.solvedTime = solvedTime;
    }
}
