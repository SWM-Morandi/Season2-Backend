package kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense;

import kr.co.morandi.backend.defense_information.domain.model.customdefense.CustomDefenseProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomDefenseProblemRepository extends JpaRepository<CustomDefenseProblem, Long> {
}
