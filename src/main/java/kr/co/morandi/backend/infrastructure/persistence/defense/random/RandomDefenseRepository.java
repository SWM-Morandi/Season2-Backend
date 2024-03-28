package kr.co.morandi.backend.infrastructure.persistence.defense.random;

import kr.co.morandi.backend.domain.defense.random.model.RandomDefense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomDefenseRepository extends JpaRepository<RandomDefense, Long> {
}
