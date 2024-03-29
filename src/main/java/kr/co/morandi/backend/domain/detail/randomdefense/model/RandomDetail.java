package kr.co.morandi.backend.domain.detail.randomdefense.model;

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
@DiscriminatorValue("RandomDefenseProblemRecord")
public class RandomDetail extends Detail {

    private Long problemNumber;
    private Long solvedTime;

    private static final long INITIAL_SOLVED_TIME = 0L;
    private RandomDetail(Member member, Long sequenceNumber, Problem problem, Record<?> records, Defense defense) {
        super(member, problem, records, defense);
        this.problemNumber = sequenceNumber;
        this.solvedTime = INITIAL_SOLVED_TIME;
    }

    public static RandomDetail create(Member member, Long sequenceNumber, Problem problem, Record<?> records, Defense defense) {
        return new RandomDetail(member, sequenceNumber, problem, records, defense);
    }
}
