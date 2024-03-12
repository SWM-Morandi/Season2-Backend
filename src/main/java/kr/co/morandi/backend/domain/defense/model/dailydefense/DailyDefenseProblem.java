package kr.co.morandi.backend.domain.defense.model.dailydefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.Problem;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyDefense DailyDefense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private static final Long INITIAL_SUBMIT_COUNT = 0L;

    private static final Long INITIAL_SOLVED_COUNT = 0L;

    @Builder
    private DailyDefenseProblem(DailyDefense DailyDefense, Problem problem) {
        this.DailyDefense = DailyDefense;
        this.problem = problem;
        this.submitCount = INITIAL_SUBMIT_COUNT;
        this.solvedCount = INITIAL_SOLVED_COUNT;
    }
    public static DailyDefenseProblem create(DailyDefense DailyDefense, Problem problem) {
        return new DailyDefenseProblem(DailyDefense, problem);
    }
}
