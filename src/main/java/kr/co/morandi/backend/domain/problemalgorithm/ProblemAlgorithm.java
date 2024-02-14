package kr.co.morandi.backend.domain.problemalgorithm;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.algorithm.Algorithm;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemAlgorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemAlgorithmId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
}
