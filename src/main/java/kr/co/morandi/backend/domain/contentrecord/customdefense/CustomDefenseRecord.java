package kr.co.morandi.backend.domain.contentrecord.customdefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.customdefense.CustomDefenseProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("CustomDefenseRecord")
public class CustomDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Integer solvedCount;
    private Integer problemCount;
    private CustomDefenseRecord(CustomDefense customDefense, Member member, LocalDateTime testDate) {
        super(testDate, customDefense, member);
        this.totalSolvedTime = 0L;
        this.solvedCount = 0;
        this.problemCount = customDefense.getProblemCount();
    }
    public static CustomDefenseRecord create(CustomDefense customDefense, Member member, LocalDateTime testDate) {
        return new CustomDefenseRecord(customDefense, member, testDate);
    }
}
