package kr.co.morandi.backend.infrastructure.persistence.defensemanagement.tempcode;

import kr.co.morandi.backend.domain.defensemanagement.tempcode.model.TempCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCodeRepository extends JpaRepository<TempCode, Long> {
}
