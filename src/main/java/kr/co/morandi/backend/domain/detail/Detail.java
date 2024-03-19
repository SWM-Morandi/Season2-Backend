package kr.co.morandi.backend.domain.detail;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Detail extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    private Boolean isSolved;

    private Long submitCount;

    private String solvedCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Defense defense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Record<?> record;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private static final Long INITIAL_SUBMIT_COUNT = 0L;
    private static final Boolean INITIAL_IS_SOLVED = false;

    protected Detail(Member member, Problem problem, Record<?> record, Defense defense) {
        this.isSolved = INITIAL_IS_SOLVED;
        this.submitCount = INITIAL_SUBMIT_COUNT;
        this.solvedCode = null;
        this.defense = defense;
        this.record = record;
        this.member = member;
        this.problem = problem;
    }
}
