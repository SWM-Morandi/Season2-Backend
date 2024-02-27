package kr.co.morandi.backend.domain.algorithm;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    Boolean existsByBojTagIdOrAlgorithmKey(Integer bojTagId, String algorithmKey);

}
