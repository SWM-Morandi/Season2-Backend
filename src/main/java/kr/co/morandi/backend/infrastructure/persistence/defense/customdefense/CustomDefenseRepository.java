package kr.co.morandi.backend.infrastructure.persistence.defense.customdefense;

import kr.co.morandi.backend.domain.defense.customdefense.model.CustomDefense;
import kr.co.morandi.backend.domain.defense.customdefense.model.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomDefenseRepository extends JpaRepository<CustomDefense, Long> {
    List<CustomDefense> findAllByVisibility(Visibility visibility);
}
