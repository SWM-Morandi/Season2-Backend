package kr.co.morandi.backend.domain.defense.model.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefenseProblem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customProblemsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomDefense customDefense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private Long submitCount;

    private Long solvedCount;

    private CustomDefenseProblem(CustomDefense customDefense, Problem problem) {
        this.customDefense = customDefense;
        this.problem = problem;
        this.submitCount = 0L;
        this.solvedCount = 0L;
    }
    @Builder
    private CustomDefenseProblem(CustomDefense customDefense, Problem problem, Long submitCount, Long solvedCount) {
        this.customDefense = customDefense;
        this.problem = problem;
        this.submitCount = submitCount;
        this.solvedCount = solvedCount;
    }
    public static CustomDefenseProblem create(CustomDefense customDefense, Problem problem) {
        return new CustomDefenseProblem(customDefense, problem);
    }
}
