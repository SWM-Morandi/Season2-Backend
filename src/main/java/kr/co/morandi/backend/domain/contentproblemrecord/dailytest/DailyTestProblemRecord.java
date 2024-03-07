package kr.co.morandi.backend.domain.contentproblemrecord.dailytest;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyTestProblemRecord")
public class DailyTestProblemRecord extends ContentProblemRecord {

    private DailyTestProblemRecord(Member member, Problem problem,
                                   ContentRecord contentRecord, ContentType contentType) {
        super(member, problem, contentRecord, contentType);
    }
    public static DailyTestProblemRecord create(Member member, Problem problem,
                                                ContentRecord contentRecord, ContentType contentType) {
        return new DailyTestProblemRecord(member, problem, contentRecord, contentType);
    }
}
