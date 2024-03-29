package kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailyDefenseProblemRepository extends JpaRepository<DailyDefenseProblem, Long> {
    @Query("""
          select ddp
          from DailyDefenseProblem as ddp
          left join fetch ddp.problem p
          where ddp.dailyDefense.defenseId = :defenseId
    """)
    List<DailyDefenseProblem> findAllProblemsContainsDefenseId(Long defenseId);
}
