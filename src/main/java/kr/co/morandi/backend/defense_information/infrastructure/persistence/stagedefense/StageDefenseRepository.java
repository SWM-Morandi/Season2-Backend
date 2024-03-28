package kr.co.morandi.backend.defense_information.infrastructure.persistence.stagedefense;

import kr.co.morandi.backend.defense_information.domain.model.stagedefense.model.StageDefense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageDefenseRepository extends JpaRepository<StageDefense, Long> {
}
