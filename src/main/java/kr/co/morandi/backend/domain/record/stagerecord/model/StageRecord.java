package kr.co.morandi.backend.domain.record.stagerecord.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.detail.stagedefense.model.StageDetail;
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
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("StageDefenseRecord")
public class StageRecord extends Record<StageDetail> {
    private Long totalSolvedTime;
    private Long stageCount;

    private static final Long INITIAL_TOTAL_SOLVED_TIME = 0L;
    private static final Long INITIAL_STAGE_NUMBER = 1L;
    private static final Long INITIAL_STAGE_COUNT = 1L;

    private StageRecord(Defense defense, LocalDateTime testDate, Member member, Map<Long, Problem> problems) {
        super(testDate, defense, member, problems);
        this.totalSolvedTime = INITIAL_TOTAL_SOLVED_TIME;
        this.stageCount = INITIAL_STAGE_COUNT;
    }
    @Override
    protected StageDetail createDetail(Member member, Long sequenceNumber, Problem problem, Record<StageDetail> records, Defense defense) {
        return StageDetail.create(member, INITIAL_STAGE_NUMBER, problem, records, defense);
    }
    public static StageRecord create(Defense defense, LocalDateTime testDate, Member member, Problem problem) {
        return new StageRecord(defense, testDate, member, Map.of(INITIAL_STAGE_NUMBER, problem));
    }
}
