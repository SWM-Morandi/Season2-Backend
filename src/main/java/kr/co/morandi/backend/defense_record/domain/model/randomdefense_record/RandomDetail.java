package kr.co.morandi.backend.defense_record.domain.model.randomdefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("RandomDefenseProblemRecord")
public class RandomDetail extends Detail {

    private Long problemNumber;
    private Long solvedTime;

    @Override
    public Long getSequenceNumber() {
        return problemNumber;
    }

    private static final long INITIAL_SOLVED_TIME = 0L;

    @Builder
    private RandomDetail(Member member, Long sequenceNumber, Problem problem, Record<? extends Detail> records, Defense defense) {
        super(member, problem, records, defense);
        this.problemNumber = sequenceNumber;
        this.solvedTime = INITIAL_SOLVED_TIME;
    }

    public static RandomDetail create(Member member, Long sequenceNumber, Problem problem, Record<? extends Detail> records, Defense defense) {
        return new RandomDetail(member, sequenceNumber, problem, records, defense);
    }
}
