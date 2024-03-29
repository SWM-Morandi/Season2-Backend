package kr.co.morandi.backend.problem_information.domain.model.problem;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.problem_information.domain.model.algorithm.Algorithm;
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
