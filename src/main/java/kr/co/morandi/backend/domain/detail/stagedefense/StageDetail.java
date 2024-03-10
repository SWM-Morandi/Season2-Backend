package kr.co.morandi.backend.domain.detail.stagedefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.record.Record;
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

    private StageDetail(Long stageNumber, Member member, Problem problem,
                                      Record record, Defense defense) {
        super(member, problem, record, defense);
        this.solvedTime = 0L;
        this.stageNumber = stageNumber;
    }
    public static StageDetail create(Long stageNumber, Member member, Problem problem,
                                                   Record record, Defense defense) {
        return new StageDetail(stageNumber, member, problem, record, defense);
    }
}
