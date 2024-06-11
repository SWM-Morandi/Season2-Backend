package kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon;

import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonGlobalCookie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonGlobalCookieRepository extends JpaRepository<BaekjoonGlobalCookie, Long> {

}
