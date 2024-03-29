package kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense;

import kr.co.morandi.backend.defense_information.domain.model.customdefense.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
}
