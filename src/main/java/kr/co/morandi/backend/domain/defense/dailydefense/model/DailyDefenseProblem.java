package kr.co.morandi.backend.domain.defense.dailydefense.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.model.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyDefenseProblem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyDefenseProblemId;

    private Long submitCount;

    private Long solvedCount;

    private Long problemNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyDefense dailyDefense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private static final Long INITIAL_SUBMIT_COUNT = 0L;

    private static final Long INITIAL_SOLVED_COUNT = 0L;

    @Builder
    private DailyDefenseProblem(DailyDefense dailyDefense, Problem problem, Long problemNumber) {
        this.dailyDefense = dailyDefense;
        this.problem = problem;
        this.submitCount = INITIAL_SUBMIT_COUNT;
        this.solvedCount = INITIAL_SOLVED_COUNT;
        this.problemNumber = problemNumber;
    }
    public static DailyDefenseProblem create(DailyDefense DailyDefense, Problem problem, Long problemNumber) {
        return new DailyDefenseProblem(DailyDefense, problem, problemNumber);
    }
}
