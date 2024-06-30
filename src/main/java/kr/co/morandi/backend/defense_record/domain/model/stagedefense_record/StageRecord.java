package kr.co.morandi.backend.defense_record.domain.model.stagedefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("StageDefenseRecord")
public class StageRecord extends Record<StageDetail> {

    private Long stageCount;

    private static final Long INITIAL_STAGE_NUMBER = 1L;
    private static final Long INITIAL_STAGE_COUNT = 1L;

    @Builder
    private StageRecord(Defense defense, LocalDateTime testDate, Member member, Map<Long, Problem> problems) {
        super(testDate, defense, member, problems);
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
