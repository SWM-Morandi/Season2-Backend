package kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonCookie;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonGlobalCookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Transactional
class BaekjoonGlobalCookieRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BaekjoonGlobalCookieRepository globalCookieRepository;

    @DisplayName("유효한 글로벌 쿠키를 찾을 수 있다.")
    @Test
    void findValidGlobalCookies() {
        // given
        LocalDateTime 현재_시간 = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        BaekjoonCookie 백준_쿠키 = BaekjoonCookie.builder()
                .cookie("cookie")
                .nowDateTime(현재_시간)
                .build();

        BaekjoonGlobalCookie 글로벌_관리_쿠키 = BaekjoonGlobalCookie.builder()
                .baekjoonCookie(백준_쿠키)
                .globalUserId("globalUserId")
                .baekjoonRefreshToken("refreshToken")
                .build();
        globalCookieRepository.save(글로벌_관리_쿠키);

        // when
        final List<BaekjoonGlobalCookie> validGlobalCookies = globalCookieRepository.findValidGlobalCookies(현재_시간);


        // then
        assertThat(validGlobalCookies).isNotEmpty()
                .extracting("baekjoonCookie", "globalUserId", "baekjoonRefreshToken")
                .containsExactly(tuple(백준_쿠키, "globalUserId", "refreshToken"));

    }

}