package kr.co.morandi.backend.domain.problemalgorithm.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.algorithm.model.Algorithm;
import kr.co.morandi.backend.domain.problem.model.Problem;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemAlgorithm extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemAlgorithmId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @Builder
    private ProblemAlgorithm(Algorithm algorithm, Problem problem) {
        this.algorithm = algorithm;
        this.problem = problem;
    }
}
