package kr.co.morandi.backend.domain.problemalgorithm;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.algorithm.Algorithm;
import kr.co.morandi.backend.domain.problem.Problem;
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
