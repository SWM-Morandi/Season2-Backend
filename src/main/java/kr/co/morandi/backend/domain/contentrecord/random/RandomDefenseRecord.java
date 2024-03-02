package kr.co.morandi.backend.domain.contentrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.random.RandomDefenseProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.contenttype.random.randomdefense.RandomDefense;
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
@DiscriminatorValue("RandomDefenseRecord")
public class RandomDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Integer solvedCount;
    private Integer problemCount;

    private static final Long INITIAL_TOTAL_SOLVED_TIME = 0L;
    private static final Integer INITIAL_SOLVED_COUNT = 0;
    private RandomDefenseRecord(LocalDateTime testDate, RandomDefense randomDefense, Member member,
                                List<Problem> problems) {
        super(testDate, randomDefense, member, problems);
        this.totalSolvedTime = INITIAL_TOTAL_SOLVED_TIME;
        this.solvedCount = INITIAL_SOLVED_COUNT;
        this.problemCount = problems.size();
    }
    @Override
    protected ContentProblemRecord createContentProblemRecord(Member member, Problem problem,
                                                              ContentRecord contentRecord, ContentType contentType) {
        return RandomDefenseProblemRecord.create(member, problem, contentRecord, contentType);
    }
    public static RandomDefenseRecord create(RandomDefense randomDefense, Member member, LocalDateTime testDate,
                                             List<Problem> problems) {

        return new RandomDefenseRecord(testDate, randomDefense, member, problems);
    }
}
