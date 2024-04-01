package kr.co.morandi.backend.defense_record.domain.model.dailydefense_record;

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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyDefenseProblemRecord")
public class DailyDetail extends Detail {

    Long problemNumber;


    private DailyDetail(Member member, Long problemNumber, Problem problem, Record<?> records, Defense defense) {
        super(member, problem, records, defense);
        this.problemNumber = problemNumber;
    }
    public static DailyDetail create(Member member, Long problemNumber, Problem problem, Record<?> records, Defense defense) {
        return new DailyDetail(member, problemNumber, problem, records, defense);
    }
}
