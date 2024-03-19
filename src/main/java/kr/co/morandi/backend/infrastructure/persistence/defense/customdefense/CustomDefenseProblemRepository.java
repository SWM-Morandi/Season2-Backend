package kr.co.morandi.backend.infrastructure.persistence.defense.customdefense;

import kr.co.morandi.backend.domain.defense.customdefense.model.CustomDefenseProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomDefenseProblemRepository extends JpaRepository<CustomDefenseProblem, Long> {
}
