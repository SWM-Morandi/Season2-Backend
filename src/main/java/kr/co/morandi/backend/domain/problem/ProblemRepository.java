package kr.co.morandi.backend.domain.problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByProblemStatus(ProblemStatus problemStatus);
}

