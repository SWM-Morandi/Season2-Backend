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

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("RandomDefenseProblemRecord")
public class RandomDefenseProblemRecord extends ContentProblemRecord {

    private Long solvedTime;

    private static final long INITIAL_SOLVED_TIME = 0L;
    private RandomDefenseProblemRecord(Member member, Problem problem,
                                       ContentRecord contentRecord, ContentType contentType) {
        super(member, problem, contentRecord, contentType);
        this.solvedTime = INITIAL_SOLVED_TIME;
    }

    public static RandomDefenseProblemRecord create(Member member, Problem problem,
                                              ContentRecord contentRecord, ContentType contentType) {
        return new RandomDefenseProblemRecord(member, problem, contentRecord, contentType);
    }
}
