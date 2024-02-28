package kr.co.morandi.backend.domain.contentrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.random.RandomDefenseProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contentrecord.customdefense.CustomDefenseRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefense;
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
    private Long solvedCount;
    private Long problemCount;

    private RandomDefenseRecord(LocalDateTime testDate, RandomDefense randomDefense, Member member,
                                List<Problem> problems, Long problemCount) {
        super(testDate, randomDefense, member, problems);
        this.totalSolvedTime = 0L;
        this.solvedCount = 0L;
        this.problemCount = problemCount;
    }
    @Override
    protected ContentProblemRecord createContentProblemRecord(ContentType contentType, ContentRecord contentRecord,
                                                              Member member, Problem problem) {
        return RandomDefenseProblemRecord.builder()
                .submitCount(0L)
                .solvedCode(null)
                .isSolved(false)
                .solvedTime(0L)
                .contentType(contentType)
                .contentRecord(contentRecord)
                .member(member)
                .problem(problem)
                .build();
    }
    public static RandomDefenseRecord create(RandomDefense randomDefense, Member member, LocalDateTime testDate,
                                             List<Problem> problems, Long problemCount) {
        return new RandomDefenseRecord(testDate, randomDefense, member, problems, problemCount);
    }
}
