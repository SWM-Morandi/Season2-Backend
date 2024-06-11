package kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.BaekjoonCookieErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonGlobalCookieTest {

    @DisplayName("BaekjoonGlobalCookie를 생성할 때, globalUserId가 null이면 예외를 던진다.")
    @Test
    void validateGlobalUserIdWhenNull() {
        // given
        BaekjoonCookie 백준_쿠키 = BaekjoonCookie.builder()
                .cookie("dummyCookie")
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        String 백준_아이디 = null;
        String 리프레시_토큰 = "testRefreshToken";


        // when & then
        assertThatThrownBy(() -> BaekjoonGlobalCookie.builder()
                .baekjoonCookie(백준_쿠키)
                .globalUserId(백준_아이디)
                .baekjoonRefreshToken(리프레시_토큰)
                .build())
            .isInstanceOf(MorandiException.class)
            .hasMessage(BaekjoonCookieErrorCode.INVALID_GLOBAL_USER_ID.getMessage());
    }

    @DisplayName("BaekjoonGlobalCookie를 생성할 때, globalUserId가 빈 문자열이면 예외를 던진다.")
    @Test
    void validateGlobalUserIdWithEmptyString() {
        // given
        BaekjoonCookie 백준_쿠키 = BaekjoonCookie.builder()
                .cookie("dummyCookie")
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        String 백준_아이디 = "  ";
        String 리프레시_토큰 = "testRefreshToken";


        // when & then
        assertThatThrownBy(() -> BaekjoonGlobalCookie.builder()
                .baekjoonCookie(백준_쿠키)
                .globalUserId(백준_아이디)
                .baekjoonRefreshToken(리프레시_토큰)
                .build())
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_GLOBAL_USER_ID.getMessage());
    }

    @DisplayName("BaekjoonGlobalCookie를 생성할 때, refreshToken이 빈 문자열이면 예외를 던진다.")
    @Test
    void validateRefreshTokenWithEmptyString() {
        // given
        BaekjoonCookie 백준_쿠키 = BaekjoonCookie.builder()
                .cookie("dummyCookie")
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        String 백준_아이디 = "testGlobalUserId";
        String 리프레시_토큰 = "  ";


        // when & then
        assertThatThrownBy(() -> BaekjoonGlobalCookie.builder()
                .baekjoonCookie(백준_쿠키)
                .globalUserId(백준_아이디)
                .baekjoonRefreshToken(리프레시_토큰)
                .build())
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_BAEKJOON_REFRESH_TOKEN.getMessage());
    }

    @DisplayName("BaekjoonGlobalCookie를 생성할 때, refreshToken이 null이면 예외를 던진다.")
    @Test
    void validateRefreshTokenWithNull() {
        // given
        BaekjoonCookie 백준_쿠키 = BaekjoonCookie.builder()
                .cookie("dummyCookie")
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .build();

        String 백준_아이디 = "testGlobalUserId";
        String 리프레시_토큰 = null;


        // when & then
        assertThatThrownBy(() -> BaekjoonGlobalCookie.builder()
                .baekjoonCookie(백준_쿠키)
                .globalUserId(백준_아이디)
                .baekjoonRefreshToken(리프레시_토큰)
                .build())
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_BAEKJOON_REFRESH_TOKEN.getMessage());
    }

}