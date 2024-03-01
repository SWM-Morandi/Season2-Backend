package kr.co.morandi.backend.domain.contentrecord.dailytest;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.dailytest.DailyTestProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.contenttype.dailytest.DailyTest;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
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
    public static DailyTestRecord create(Long problemCount, LocalDateTime date, DailyTest dailyTest,
                                         Member member, List<Problem> problems) {

        if (Duration.between(dailyTest.getDate(), date).toDays() >= 1)
            throw new IllegalArgumentException("오늘의 문제 기록은 출제 시점으로부터 하루 이내에 생성되어야 합니다.");

        return new DailyTestRecord(problemCount, date, dailyTest, member, problems);
    }
}
