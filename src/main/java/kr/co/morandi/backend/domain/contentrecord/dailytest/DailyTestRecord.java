package kr.co.morandi.backend.domain.contentrecord.dailytest;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.dailytest.DailyTestProblemRecord;
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
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyTestRecord")
public class DailyTestRecord extends ContentRecord {
    private Long solvedCount;
    private Long problemCount;

    private DailyTestRecord(Long problemCount, LocalDateTime date, ContentType contentType,
                            Member member, List<Problem> problems) {
        super(date, contentType, member, problems);
        this.solvedCount = 0L;
        this.problemCount = problemCount;
    }
    @Override
    protected ContentProblemRecord createContentProblemRecord(Member member, Problem problem,
                                                              ContentRecord contentRecord, ContentType contentType) {
        return DailyTestProblemRecord.create(member, problem, contentRecord, contentType);
    }
    public static DailyTestRecord create(Long problemCount, LocalDateTime date, ContentType contentType,
                                         Member member, List<Problem> problems) {
        return new DailyTestRecord(problemCount, date, contentType, member, problems);
    }
}
