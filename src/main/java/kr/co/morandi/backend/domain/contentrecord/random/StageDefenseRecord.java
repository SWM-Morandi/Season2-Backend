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

    private static final Long INITIAL_TOTAL_SOLVED_TIME = 0L;
    private static final Long INITIAL_STAGE_NUMBER = 1L;
    private static final Long INITIAL_STAGE_COUNT = 1L;
    private StageDefenseRecord(ContentType contentType, LocalDateTime testDate,
                               Member member, List<Problem> problems) {
        super(testDate, contentType, member, problems);
        this.totalSolvedTime = INITIAL_TOTAL_SOLVED_TIME;
        this.stageCount = INITIAL_STAGE_COUNT;
    }
    @Override
    protected ContentProblemRecord createContentProblemRecord(Member member, Problem problem,
                                                              ContentRecord contentRecord, ContentType contentType) {
        return StageDefenseProblemRecord.create(INITIAL_STAGE_NUMBER, member, problem, contentRecord, contentType);
    }
    public static StageDefenseRecord create(ContentType contentType, LocalDateTime testDate,
                                            Member member, Problem problem) {
        return new StageDefenseRecord(contentType, testDate, member, List.of(problem));
    }
}
