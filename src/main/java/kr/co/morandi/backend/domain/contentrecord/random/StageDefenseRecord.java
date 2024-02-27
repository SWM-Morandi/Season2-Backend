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

    @Override
    protected ContentProblemRecord createContentProblemRecord(ContentType contentType, ContentRecord contentRecord, Member member, Problem problem) {
        return StageDefenseProblemRecord.builder()
                .contentType(contentType)
                .contentRecord(contentRecord)
                .member(member)
                .problem(problem)
                .build();
    }
}
