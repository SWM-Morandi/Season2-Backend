package kr.co.morandi.backend.domain.detail.customdefense.model;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("CustomDefenseProblemRecord")
public class CustomDetail extends Detail {

    private Long problemNumber;
    private Long solvedTime;

    private static final long INITIAL_SOLVED_TIME = 0L;
    private CustomDetail(Member member, Long sequenceNumber, Problem problem, Record<?> records, Defense defense) {
        super(member, problem, records, defense);
        this.problemNumber = sequenceNumber;
        this.solvedTime = INITIAL_SOLVED_TIME;
    }
    public static CustomDetail create(Member member, Long sequenceNumber, Problem problem, Record<?> records, Defense defense) {
        return new CustomDetail(member, sequenceNumber, problem, records, defense);
    }
}
