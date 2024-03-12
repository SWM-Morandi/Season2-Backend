package kr.co.morandi.backend.domain.detail.customdefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.model.Defense;
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
@DiscriminatorValue("CustomDefenseProblemRecord")
public class CustomDetail extends Detail {

    private Long solvedTime;

    private static final long INITIAL_SOLVED_TIME = 0L;
    private CustomDetail(Member member, Problem problem, Record record, Defense defense) {
        super(member, problem, record, defense);
        this.solvedTime = INITIAL_SOLVED_TIME;
    }
    public static CustomDetail create(Member member, Problem problem, Record record, Defense defense) {
        return new CustomDetail(member, problem, record, defense);
    }
}
