package kr.co.morandi.backend.infrastructure.persistence.problem;

import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.problem.model.ProblemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByProblemStatus(ProblemStatus problemStatus);
}

