package kr.co.morandi.backend.domain.contentproblemrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("StageDefenseProblemRecord")
public class StageDefenseProblemRecord extends ContentProblemRecord {
    private Long solvedTime;
    private Long stageNumber;

    private StageDefenseProblemRecord(Long stageNumber, Member member, Problem problem,
                                      ContentRecord contentRecord, ContentType contentType) {
        super(member, problem, contentRecord, contentType);
        this.solvedTime = 0L;
        this.stageNumber = stageNumber;
    }
    public static StageDefenseProblemRecord create(Long stageNumber, Member member, Problem problem,
                                                   ContentRecord contentRecord, ContentType contentType) {
        return new StageDefenseProblemRecord(stageNumber, member, problem, contentRecord, contentType);
    }
}
