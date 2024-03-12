package kr.co.morandi.backend.domain.detail.dailydefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyDefenseProblemRecord")
public class DailyDetail extends Detail {

    private DailyDetail(Member member, Problem problem, Record record, Defense defense) {
        super(member, problem, record, defense);
    }
    public static DailyDetail create(Member member, Problem problem,
                                                   Record record, Defense defense) {
        return new DailyDetail(member, problem, record, defense);
    }
}
