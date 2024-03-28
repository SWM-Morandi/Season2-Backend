package kr.co.morandi.backend.infrastructure.persistence.defense.stagedefense;

import kr.co.morandi.backend.domain.defense.stagedefense.model.StageDefense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageDefenseRepository extends JpaRepository<StageDefense, Long> {
}
