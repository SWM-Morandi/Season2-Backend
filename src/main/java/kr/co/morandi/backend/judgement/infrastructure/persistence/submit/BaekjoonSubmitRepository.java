package kr.co.morandi.backend.judgement.infrastructure.persistence.submit;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaekjoonSubmitRepository extends JpaRepository<BaekjoonSubmit, Long> {
}
