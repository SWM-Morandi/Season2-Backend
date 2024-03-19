package kr.co.morandi.backend.infrastructure.persistence.defense;

import kr.co.morandi.backend.domain.defense.Defense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefenseRepository extends JpaRepository<Defense, Long> {
}
