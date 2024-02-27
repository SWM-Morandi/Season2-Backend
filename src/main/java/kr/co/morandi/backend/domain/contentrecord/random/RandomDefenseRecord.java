package kr.co.morandi.backend.domain.contentrecord.random;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentproblemrecord.random.RandomDefenseProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("RandomDefenseRecord")
public class RandomDefenseRecord extends ContentRecord {
    private Long totalSolvedTime;
    private Long solvedCount;
    private Long problemCount;

    private RandomDefenseRecord(Long totalSolvedTime, Long solvedCount, Long problemCount) {
        this.totalSolvedTime = totalSolvedTime;
        this.solvedCount = solvedCount;
        this.problemCount = problemCount;
    }

    @Override
    protected ContentProblemRecord createContentProblemRecord(ContentType contentType, ContentRecord contentRecord, Member member, Problem problem) {
        return RandomDefenseProblemRecord.builder()
                .contentType(contentType)
                .contentRecord(contentRecord)
                .member(member)
                .problem(problem)
                .build();
    }
}
