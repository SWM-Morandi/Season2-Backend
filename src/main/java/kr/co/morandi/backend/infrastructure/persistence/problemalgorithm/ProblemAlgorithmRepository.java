package kr.co.morandi.backend.infrastructure.persistence.problemalgorithm;

import kr.co.morandi.backend.domain.problemalgorithm.model.ProblemAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemAlgorithmRepository extends JpaRepository<ProblemAlgorithm, Long> {
}
