package kr.co.morandi.backend.infrastructure.persistence.defensememberlikes;

import kr.co.morandi.backend.domain.contentmemberlikes.model.ContentMemberLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentMemberLikesRepository extends JpaRepository<ContentMemberLikes, Long> {
}
