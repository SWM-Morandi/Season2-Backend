package kr.co.morandi.backend.domain.record.customdefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.detail.ContentProblemRecord;
import kr.co.morandi.backend.domain.detail.customdefense.CustomDefenseProblemRecord;
import kr.co.morandi.backend.domain.record.ContentRecord;
import kr.co.morandi.backend.domain.defense.ContentType;
import kr.co.morandi.backend.domain.defense.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("CustomDefenseRecord")
public class CustomDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Integer solvedCount;
    private Integer problemCount;
    @Override
    public ContentProblemRecord createContentProblemRecord(Member member, Problem problem,
                                                           ContentRecord contentRecord, ContentType contentType) {
        return CustomDefenseProblemRecord.create(member, problem, contentRecord, contentType);
    }
    private CustomDefenseRecord(CustomDefense customDefense, Member member, LocalDateTime testDate,
                                List<Problem> problems) {
        super(testDate, customDefense, member, problems);
        this.totalSolvedTime = 0L;
        this.solvedCount = 0;
        this.problemCount = customDefense.getProblemCount();
    }
    public static CustomDefenseRecord create(CustomDefense customDefense, Member member, LocalDateTime testDate,
                                             List<Problem> problems) {
        return new CustomDefenseRecord(customDefense, member, testDate, problems);
    }
}
