package kr.co.morandi.backend.domain.record.randomrecord.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.random.model.RandomDefense;
import kr.co.morandi.backend.domain.detail.randomdefense.model.RandomDetail;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("RandomDefenseRecord")
public class RandomRecord extends Record<RandomDetail> {
    private Long totalSolvedTime;
    private Integer solvedCount;
    private Integer problemCount;

    private static final Long INITIAL_TOTAL_SOLVED_TIME = 0L;
    private static final Integer INITIAL_SOLVED_COUNT = 0;

    private RandomRecord(LocalDateTime testDate, RandomDefense randomDefense, Member member, Map<Long, Problem> problems) {
        super(testDate, randomDefense, member, problems);
        this.totalSolvedTime = INITIAL_TOTAL_SOLVED_TIME;
        this.solvedCount = INITIAL_SOLVED_COUNT;
        this.problemCount = problems.size();
    }
    @Override
    protected RandomDetail createDetail(Member member, Long sequenceNumber, Problem problem, Record<RandomDetail> record, Defense defense) {
        return RandomDetail.create(member, sequenceNumber, problem, record, defense);
    }
    public static RandomRecord create(RandomDefense randomDefense, Member member, LocalDateTime testDate, Map<Long, Problem> problems) {
        return new RandomRecord(testDate, randomDefense, member, problems);
    }
}
