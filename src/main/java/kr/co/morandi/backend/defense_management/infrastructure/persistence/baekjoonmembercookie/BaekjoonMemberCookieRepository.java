package kr.co.morandi.backend.defense_management.infrastructure.persistence.baekjoonmembercookie;

import kr.co.morandi.backend.defense_management.domain.model.baekjooncookie.BaekjoonMemberCookie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonMemberCookieRepository extends JpaRepository<BaekjoonMemberCookie, Long> {

}
