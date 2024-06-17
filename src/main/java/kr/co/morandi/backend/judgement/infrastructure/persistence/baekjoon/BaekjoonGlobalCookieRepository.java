package kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonGlobalCookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BaekjoonGlobalCookieRepository extends JpaRepository<BaekjoonGlobalCookie, Long> {

    @Query("""
        SELECT cookie
        FROM BaekjoonGlobalCookie cookie
        WHERE cookie.baekjoonCookie.expiredAt > :now
    """)
    List<BaekjoonGlobalCookie> findValidGlobalCookies(LocalDateTime now);
}
