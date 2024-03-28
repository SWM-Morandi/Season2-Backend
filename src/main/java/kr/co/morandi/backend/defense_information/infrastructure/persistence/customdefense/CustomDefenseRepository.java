package kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense;

import kr.co.morandi.backend.defense_information.domain.model.customdefense.CustomDefense;
import kr.co.morandi.backend.defense_information.domain.model.customdefense.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomDefenseRepository extends JpaRepository<CustomDefense, Long> {
    List<CustomDefense> findAllByVisibility(Visibility visibility);
}
