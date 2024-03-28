package kr.co.morandi.backend.infrastructure.persistence.algorithm;

import kr.co.morandi.backend.domain.algorithm.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    Boolean existsByBojTagIdOrAlgorithmKey(Integer bojTagId, String algorithmKey);

}
