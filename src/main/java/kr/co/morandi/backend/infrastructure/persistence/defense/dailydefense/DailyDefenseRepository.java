package kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyDefenseRepository extends JpaRepository<DailyDefense, Long> {

    Optional<DailyDefense> findDailyDefenseByDefenseTypeAndDate(DefenseType defenseType, LocalDate date);

}
