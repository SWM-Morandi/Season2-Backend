package kr.co.morandi.backend.domain.problem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    private Long baekjoonProblemId;

    private String problemDifficulty;

    private Long solvedCount;
}
