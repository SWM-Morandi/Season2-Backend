package kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense;

import kr.co.morandi.backend.defense_information.domain.model.customdefense.ContentMemberLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentMemberLikesRepository extends JpaRepository<ContentMemberLikes, Long> {
}
