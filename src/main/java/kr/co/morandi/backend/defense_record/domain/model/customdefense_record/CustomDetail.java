package kr.co.morandi.backend.defense_record.domain.model.customdefense_record;

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
