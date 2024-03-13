package kr.co.morandi.backend.domain.defense.model.dailydefense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailyDefenseProblemRepository extends JpaRepository<DailyDefenseProblem, Long> {
    @Query("""
          select ddp
          from DailyDefenseProblem as ddp
          left join fetch ddp.problem p
          where ddp.DailyDefense.defenseId = :defenseId
    """)
    List<DailyDefenseProblem> findAllProblemsContainsDefenseId(Long defenseId);
}
