package kr.co.morandi.backend.problem_information.infrastructure.persistence.algorithm;

import kr.co.morandi.backend.problem_information.domain.model.algorithm.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    Boolean existsByBojTagIdOrAlgorithmKey(Integer bojTagId, String algorithmKey);

}
