package kr.co.morandi.backend.domain.problem;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.algorithm.Algorithm;
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
    @JoinColumn(name = "ALGORITHM_ID")
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROBLEM_ID")
    private Problem problem;
}
