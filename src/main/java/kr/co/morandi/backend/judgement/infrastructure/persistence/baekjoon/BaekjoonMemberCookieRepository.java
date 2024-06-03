package kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonMemberCookieRepository extends JpaRepository<BaekjoonMemberCookie, Long> {

}
