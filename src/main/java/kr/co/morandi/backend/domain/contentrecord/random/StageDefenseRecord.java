package kr.co.morandi.backend.domain.contentrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.random.StageDefenseProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("StageDefenseRecord")
public class StageDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Long stageCount;
    private static final Long startNumber = 1L;
    private StageDefenseRecord(Long stageCount, ContentType contentType, LocalDateTime testDate,
                               Member member, List<Problem> problems) {
        super(testDate, contentType, member, problems);
        this.totalSolvedTime = 0L;
        this.stageCount = stageCount;
    }
    @Override
    protected ContentProblemRecord createContentProblemRecord(Member member, Problem problem,
                                                              ContentRecord contentRecord, ContentType contentType) {
        return StageDefenseProblemRecord.create(startNumber, member, problem, contentRecord, contentType);
    }
    public static StageDefenseRecord create(Long stageCount, ContentType contentType, LocalDateTime testDate,
                                            Member member, List<Problem> problems) {
        return new StageDefenseRecord(stageCount, contentType, testDate, member, problems);
    }
}
