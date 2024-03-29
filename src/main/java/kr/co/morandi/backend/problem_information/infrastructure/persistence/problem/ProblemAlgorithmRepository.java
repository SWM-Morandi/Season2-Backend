package kr.co.morandi.backend.problem_information.infrastructure.persistence.problem;

import kr.co.morandi.backend.problem_information.domain.model.problem.ProblemAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemAlgorithmRepository extends JpaRepository<ProblemAlgorithm, Long> {
}
