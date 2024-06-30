package kr.co.morandi.backend.defense_record.domain.model.stagedefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("StageDefenseProblemRecord")
public class StageDetail extends Detail {

    private Long stageNumber;

    @Override
    public Long getSequenceNumber() {
        return stageNumber;
    }

    @Builder
    private StageDetail(Member member, Long stageNumber, Problem problem, Record<? extends Detail> records, Defense defense) {
        super(member, problem, records, defense);
        this.stageNumber = stageNumber;
    }
    public static StageDetail create(Member member, Long stageNumber, Problem problem, Record<? extends Detail> records, Defense defense) {
        return new StageDetail(member, stageNumber, problem, records, defense);
    }
}
