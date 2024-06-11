package kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaekjoonMemberCookieRepository extends JpaRepository<BaekjoonMemberCookie, Long> {
    Optional<BaekjoonMemberCookie> findBaekjoonMemberCookieByMember_MemberId(Long memberId);
}
