package kr.co.morandi.backend.domain.contentproblemrecord.customdefense;

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
@DiscriminatorValue("CustomDefenseProblemRecord")
public class CustomDefenseProblemRecord extends ContentProblemRecord {

    private Long solvedTime;
    private CustomDefenseProblemRecord(ContentType contentType, ContentRecord contentRecord,
                                       Member member, Problem problem) {
        super(contentType, contentRecord, member, problem);
        this.solvedTime = 0L;
    }
    public static CustomDefenseProblemRecord create(ContentType contentType, ContentRecord contentRecord,
                                                    Member member, Problem problem) {
        return new CustomDefenseProblemRecord(contentType, contentRecord, member, problem);
    }
}
