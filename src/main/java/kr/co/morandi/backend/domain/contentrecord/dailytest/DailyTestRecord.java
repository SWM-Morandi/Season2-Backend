package kr.co.morandi.backend.domain.contentrecord.dailytest;

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
@DiscriminatorValue("DailyTestRecord")
public class DailyTestRecord extends ContentRecord {
    private Long solvedCount;
    private Long problemCount;

    private DailyTestRecord(Long solvedCount, Long problemCount) {
        this.solvedCount = solvedCount;
        this.problemCount = problemCount;
    }
}
