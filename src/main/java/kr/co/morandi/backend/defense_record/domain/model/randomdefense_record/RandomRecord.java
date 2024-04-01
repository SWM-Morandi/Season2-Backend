package kr.co.morandi.backend.defense_record.domain.model.randomdefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.randomdefense.model.RandomDefense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("RandomDefenseRecord")
public class RandomRecord extends Record<RandomDetail> {

    private Integer solvedCount;
    private Integer problemCount;

    private static final Integer INITIAL_SOLVED_COUNT = 0;

    private RandomRecord(LocalDateTime testDate, RandomDefense randomDefense, Member member, Map<Long, Problem> problems) {
        super(testDate, randomDefense, member, problems);
        this.solvedCount = INITIAL_SOLVED_COUNT;
        this.problemCount = problems.size();
    }
    @Override
    protected RandomDetail createDetail(Member member, Long sequenceNumber, Problem problem, Record<RandomDetail> records, Defense defense) {
        return RandomDetail.create(member, sequenceNumber, problem, records, defense);
    }
    public static RandomRecord create(RandomDefense randomDefense, Member member, LocalDateTime testDate, Map<Long, Problem> problems) {
        return new RandomRecord(testDate, randomDefense, member, problems);
    }
}
