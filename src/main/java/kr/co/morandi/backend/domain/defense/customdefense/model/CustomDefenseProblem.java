package kr.co.morandi.backend.domain.defense.customdefense.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.model.Problem;
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

    private Long problemNumber;

    private CustomDefenseProblem(CustomDefense customDefense, Long problemNumber, Problem problem) {
        this.customDefense = customDefense;
        this.problem = problem;
        this.submitCount = 0L;
        this.problemNumber = problemNumber;
        this.solvedCount = 0L;
    }
    public static CustomDefenseProblem create(CustomDefense customDefense, Long problemNumber, Problem problem) {
        return new CustomDefenseProblem(customDefense, problemNumber, problem);
    }
    @Builder
    private CustomDefenseProblem(CustomDefense customDefense, Long problemNumber, Problem problem, Long submitCount, Long solvedCount) {
        this.customDefense = customDefense;
        this.problem = problem;
        this.submitCount = submitCount;
        this.problemNumber = problemNumber;
        this.solvedCount = solvedCount;
    }
}
