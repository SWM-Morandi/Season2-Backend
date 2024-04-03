package kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyDefenseRepository extends JpaRepository<DailyDefense, Long> {

    @Query("""
        SELECT dd
        from DailyDefense dd
        left join fetch dd.dailyDefenseProblems ddp
        left join fetch ddp.problem
        where dd.defenseType = :defenseType
        and dd.date = :date
    """)
    Optional<DailyDefense> findDailyDefenseByTypeAndDate(DefenseType defenseType, LocalDate date);

}
