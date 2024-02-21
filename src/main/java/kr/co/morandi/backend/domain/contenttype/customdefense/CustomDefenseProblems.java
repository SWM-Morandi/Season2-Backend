package kr.co.morandi.backend.domain.contenttype.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefenseProblems extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customProblemsId;

    private Long submitCount;

    private Long solvedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomDefense customDefense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
    @Builder
    private CustomDefenseProblems(Long submitCount, Long solvedCount, CustomDefense customDefense, Problem problem) {
        this.submitCount = submitCount;
        this.solvedCount = solvedCount;
        this.customDefense = customDefense;
        this.problem = problem;
    }
}
