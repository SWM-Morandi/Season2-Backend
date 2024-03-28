package kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense;

import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyDefenseRepository extends JpaRepository<DailyDefense, Long> {

    @Query("""
        SELECT dd
        from DailyDefense dd
        left join fetch dd.dailyDefenseProblems ddp
        where dd.defenseType = :defenseType
        and dd.date = :date
    """)
    Optional<DailyDefense> findDailyDefenseByTypeAndDate(DefenseType defenseType, LocalDate date);

}
