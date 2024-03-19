package kr.co.morandi.backend.domain.detail.dailydefense.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyDefenseProblemRecord")
public class DailyDetail extends Detail {

    Long problemNumber;

    private DailyDetail(Member member, Long problemNumber, Problem problem, Record<?> record, Defense defense) {
        super(member, problem, record, defense);
        this.problemNumber = problemNumber;
    }
    public static DailyDetail create(Member member, Long problemNumber, Problem problem, Record<?> record, Defense defense) {
        return new DailyDetail(member, problemNumber, problem, record, defense);
    }
}
