package kr.co.morandi.backend.defense_information.infrastructure.persistence.randomdefense;

import kr.co.morandi.backend.defense_information.domain.model.randomdefense.model.RandomDefense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomDefenseRepository extends JpaRepository<RandomDefense, Long> {
}
