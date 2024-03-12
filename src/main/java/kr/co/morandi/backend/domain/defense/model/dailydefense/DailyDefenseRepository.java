package kr.co.morandi.backend.domain.defense.model.dailydefense;

import kr.co.morandi.backend.domain.defense.model.DefenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyDefenseRepository extends JpaRepository<DailyDefense, Long> {

    Optional<DailyDefense> findDailyDefenseByDefenseTypeAndDate(DefenseType defenseType, LocalDate date);

}
