package kr.co.morandi.backend.infrastructure.persistence.bookmark;

import kr.co.morandi.backend.domain.bookmark.model.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
}
