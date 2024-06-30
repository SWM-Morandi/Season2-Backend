package kr.co.morandi.backend.defense_record.domain.model.record;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Detail extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    private Boolean isSolved;

    private Long submitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Defense defense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Record<? extends Detail> record;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private Long correctSubmitId;

    private Long solvedTime;

    public abstract Long getSequenceNumber();

    private static final Long INITIAL_SUBMIT_COUNT = 0L;
    private static final Long INITIAL_SOLVED_TIME = 0L;
    private static final Boolean INITIAL_IS_SOLVED = false;

    public void trySolveProblem(Long submitId, LocalDateTime solvedDateTime) {
        if(isSolvedDetail()) {
            return ;
        }
        this.isSolved = true;
        this.correctSubmitId = submitId;
        this.solvedTime = calculateSolvedTime(solvedDateTime);
        record.addSolvedCountAndTime(this.solvedTime);
    }
    public void increaseSubmitCount() {
        this.submitCount++;
    }

    private boolean isSolvedDetail() {
        return Boolean.TRUE.equals(this.isSolved);
    }

    private long calculateSolvedTime(LocalDateTime nowDateTime) {
        LocalDateTime startTime = this.record.getTestDate();
        return Duration.between(startTime, nowDateTime).toSeconds();
    }

    protected Detail(Member member, Problem problem, Record<? extends Detail> records, Defense defense) {
        this.isSolved = INITIAL_IS_SOLVED;
        this.submitCount = INITIAL_SUBMIT_COUNT;
        this.solvedTime = INITIAL_SOLVED_TIME;
        this.correctSubmitId = null;
        this.defense = defense;
        this.record = records;
        this.member = member;
        this.problem = problem;
    }

}
