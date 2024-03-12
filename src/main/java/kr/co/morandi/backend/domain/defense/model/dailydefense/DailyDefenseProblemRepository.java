package kr.co.morandi.backend.domain.defense.model.dailydefense;

import kr.co.morandi.backend.domain.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailyDefenseProblemRepository extends JpaRepository<DailyDefenseProblem, Long> {
    @Query("""
          select ddp.problem
          from DailyDefenseProblem as ddp
          left join ddp.problem p
          where ddp.DailyDefense.defenseId = :defenseId
    """)
    List<Problem> findAllProblemsContainsDefenseId(Long defenseId);
}
