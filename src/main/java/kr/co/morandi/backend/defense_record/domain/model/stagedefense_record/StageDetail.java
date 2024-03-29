package kr.co.morandi.backend.defense_record.domain.model.stagedefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("StageDefenseProblemRecord")
public class StageDetail extends Detail {
    private Long solvedTime;
    private Long stageNumber;

    private StageDetail(Member member, Long stageNumber, Problem problem, Record<?> records, Defense defense) {
        super(member, problem, records, defense);
        this.solvedTime = 0L;
        this.stageNumber = stageNumber;
    }
    public static StageDetail create(Member member, Long stageNumber, Problem problem, Record<?> records, Defense defense) {
        return new StageDetail(member, stageNumber, problem, records, defense);
    }
}
