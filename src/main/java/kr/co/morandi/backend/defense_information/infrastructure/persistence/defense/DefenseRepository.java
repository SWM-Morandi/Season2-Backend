package kr.co.morandi.backend.defense_information.infrastructure.persistence.defense;

import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefenseRepository extends JpaRepository<Defense, Long> {
}
