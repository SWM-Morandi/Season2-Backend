package kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.BaekjoonCookieErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonCookieTest {

    @DisplayName("백준 쿠키를 빈 값으로 업데이트 할 수 없다.")
    @Test
    void updateCookieWithEmptyValue() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        String 새로운_쿠키 = " ";
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);

        // when & then
        assertThatThrownBy(() -> baekjoonCookie.updateCookie(새로운_쿠키, 다시_로그인_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());

    }

    @DisplayName("백준 쿠키를 null 값으로 업데이트 할 수 없다.")
    @Test
    void updateCookieWithNullValue() {
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        String 새로운_쿠키 = null;
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);

        // when & then
        assertThatThrownBy(() -> baekjoonCookie.updateCookie(새로운_쿠키, 다시_로그인_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

    @DisplayName("백준 쿠키를 업데이트하는 시간이 null인 경우 업데이트 할 수 없다.")
    @Test
    void updateCookieWithNullNowDateTime() {
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        String 새로운_쿠키 = "newCookie";
        LocalDateTime 다시_로그인_시간 = null;
        // when & then
        assertThatThrownBy(() -> baekjoonCookie.updateCookie(새로운_쿠키, 다시_로그인_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

    @DisplayName("백준 쿠키 상태를 로그아웃 상태로 변경할 수 있다.")
    @Test
    void setLoggedOut() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 로그아웃_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);

        // when
        baekjoonCookie.setLoggedOut(로그아웃_시간);

        // then
        assertThat(baekjoonCookie)
                .extracting("cookieStatus", "expiredAt")
                .contains(CookieStatus.LOGGED_OUT, 로그아웃_시간);

    }

    @DisplayName("이미 로그아웃 상태인 백준 쿠키는 다시 로그아웃 상태로 변경할 수 없다.")
    @Test
    void setLoggedOutWhenAlreadyLoggedOut() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 로그아웃_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);
        baekjoonCookie.setLoggedOut(로그아웃_시간);

        // when & then
        assertThatThrownBy(() -> baekjoonCookie.setLoggedOut(로그아웃_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.ALREADY_LOGGED_OUT.getMessage());
    }

    @DisplayName("백준 쿠키를 만료된 상태에서 유효한 상태로 변경할 수 있다.")
    @Test
    void updateCookieWhenExpired() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();
        baekjoonCookie.setLoggedOut(LocalDateTime.of(2021, 1, 1, 6, 0));

        String 새로운_쿠키 = "newCookie";
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);


        // when
        baekjoonCookie.updateCookie(새로운_쿠키, 다시_로그인_시간);

        // then
        assertThat(baekjoonCookie)
                .extracting("cookieStatus", "expiredAt")
                .contains(CookieStatus.LOGGED_IN, 다시_로그인_시간.plusHours(6));
    }

    @DisplayName("백준 쿠키를 만료된 상태에서 유효한 상태로 변경할 수 있다.")
    @Test
    void updateCookie() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        String 새로운_쿠키 = "newCookie";
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);


        // when
        baekjoonCookie.updateCookie(새로운_쿠키, 다시_로그인_시간);

        // then
        assertThat(baekjoonCookie)
                .extracting("cookieStatus", "expiredAt")
                .contains(CookieStatus.LOGGED_IN, 다시_로그인_시간.plusHours(6));

    }

    @DisplayName("백준 쿠키가 유효한지 확인할 수 있다.")
    @Test
    void validateCookie() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        // when
        boolean isValidCookie = baekjoonCookie.isValidCookie(LocalDateTime.of(2021, 1, 1, 3, 0));

        // then
        assertThat(isValidCookie).isTrue();

    }
    @DisplayName("로그아웃된 백준 쿠키는 Valid하지 않다.")
    @Test
    void validateCookieWhenLoggedOut() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie 백준_쿠키 = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 만료된_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);
        백준_쿠키.setLoggedOut(만료된_시간);


        // when
        final boolean validCookie = 백준_쿠키.isValidCookie(LocalDateTime.of(2021, 1, 1, 3, 0));

        // then
        assertThat(validCookie).isFalse();

    }

    @DisplayName("만료 시간이 지난 경우 백준 쿠키는 Valid하지 않다.")
    @Test
    void validateCookieWhenExpired() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        BaekjoonCookie baekjoonCookie = BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 만료된_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);

        // when
        final boolean validCookie = baekjoonCookie.isValidCookie(만료된_시간);

        // then
        assertThat(validCookie).isFalse();

    }

    @DisplayName("빈 쿠키값으로 백준 쿠키를 생성할 수 없다.")
    @Test
    void createBaekjoonCookieWithInvalidCookieValue() {
        // given
        String 쿠키 = " ";

        // when & then
        assertThatThrownBy(() -> BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build()
        )
        .isInstanceOf(MorandiException.class)
        .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

    @DisplayName("null인 쿠키값으로 백준 쿠키를 생성할 수 없다.")
    @Test
    void createBaekjoonCookieWithNullCookieValue() {
        // given
        String 쿠키 = null;

        // when & then
        assertThatThrownBy(() -> BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build()
        )
        .isInstanceOf(MorandiException.class)
        .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

    @DisplayName("현재 시간이 null인 경우 백준 쿠키를 생성할 수 없다.")
    @Test
    void createBaekjoonCookieWithNullNowDateTime() {
        // given
        String 쿠키 = "testCookie";
        LocalDateTime 등록된_시간 = null;

        // when & then
        assertThatThrownBy(() -> BaekjoonCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build()
        )
        .isInstanceOf(MorandiException.class)
        .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

}