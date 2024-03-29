package kr.co.morandi.backend.defense_management.infrastructure.persistence.tempcode;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempCodeRepository extends JpaRepository<TempCode, Long> {
}
