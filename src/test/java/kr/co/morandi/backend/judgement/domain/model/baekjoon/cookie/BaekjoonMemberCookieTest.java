package kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.judgement.domain.error.BaekjoonCookieErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaekjoonMemberCookieTest {
    @DisplayName("백준 쿠키를 빈 값으로 업데이트 할 수 없다.")
    @Test
    void updateCookieWithEmptyValue() {
        // given
        String 쿠키 = "testCookie";
        final Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .member(사용자)
                .build();

        String 새로운_쿠키 = " ";
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);

        // when & then
        assertThatThrownBy(() -> 백준_사용자_쿠키.updateCookie(새로운_쿠키, 다시_로그인_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());

    }

    @DisplayName("백준 쿠키를 null 값으로 업데이트 할 수 없다.")
    @Test
    void updateCookieWithNullValue() {
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);


        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        String 새로운_쿠키 = null;
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);

        // when & then
        assertThatThrownBy(() -> 백준_사용자_쿠키.updateCookie(새로운_쿠키, 다시_로그인_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

    @DisplayName("백준 쿠키를 업데이트하는 시간이 null인 경우 업데이트 할 수 없다.")
    @Test
    void updateCookieWithNullNowDateTime() {
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        String 새로운_쿠키 = "newCookie";
        LocalDateTime 다시_로그인_시간 = null;
        // when & then
        assertThatThrownBy(() -> 백준_사용자_쿠키.updateCookie(새로운_쿠키, 다시_로그인_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE.getMessage());
    }

    @DisplayName("백준 쿠키 상태를 로그아웃 상태로 변경할 수 있다.")
    @Test
    void setLoggedOut() {
        // given
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 로그아웃_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);

        // when
        백준_사용자_쿠키.setLoggedOut(로그아웃_시간);

        // then
        assertThat(백준_사용자_쿠키)
                .extracting("baekjoonCookie.cookieStatus", "baekjoonCookie.expiredAt")
                .contains(CookieStatus.LOGGED_OUT, 로그아웃_시간);

    }

    @DisplayName("이미 로그아웃 상태인 백준 쿠키는 다시 로그아웃 상태로 변경할 수 없다.")
    @Test
    void setLoggedOutWhenAlreadyLoggedOut() {
        // given
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 로그아웃_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);
        백준_사용자_쿠키.setLoggedOut(로그아웃_시간);

        // when & then
        assertThatThrownBy(() -> 백준_사용자_쿠키.setLoggedOut(로그아웃_시간))
                .isInstanceOf(MorandiException.class)
                .hasMessage(BaekjoonCookieErrorCode.ALREADY_LOGGED_OUT.getMessage());
    }

    @DisplayName("백준 쿠키를 만료된 상태에서 유효한 상태로 변경할 수 있다.")
    @Test
    void updateCookieWhenExpired() {
        // given
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        백준_사용자_쿠키.setLoggedOut(LocalDateTime.of(2021, 1, 1, 6, 0));

        String 새로운_쿠키 = "newCookie";
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);


        // when
        백준_사용자_쿠키.updateCookie(새로운_쿠키, 다시_로그인_시간);

        // then
        assertThat(백준_사용자_쿠키)
                .extracting("baekjoonCookie.cookieStatus", "baekjoonCookie.expiredAt")
                .contains(CookieStatus.LOGGED_IN, 다시_로그인_시간.plusHours(6));
    }

    @DisplayName("백준 쿠키를 만료된 상태에서 유효한 상태로 변경할 수 있다.")
    @Test
    void updateCookie() {
        // given
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();


        String 새로운_쿠키 = "newCookie";
        LocalDateTime 다시_로그인_시간 = LocalDateTime.of(2021, 1, 1, 3, 0);


        // when
        백준_사용자_쿠키.updateCookie(새로운_쿠키, 다시_로그인_시간);

        // then
        assertThat(백준_사용자_쿠키)
                .extracting("baekjoonCookie.cookieStatus", "baekjoonCookie.expiredAt")
                .contains(CookieStatus.LOGGED_IN, 다시_로그인_시간.plusHours(6));

    }

    @DisplayName("백준 쿠키가 유효한지 확인할 수 있다.")
    @Test
    void validateCookie() {
        // given
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        // when
        boolean isValidCookie = 백준_사용자_쿠키.isValidCookie(LocalDateTime.of(2021, 1, 1, 3, 0));

        // then
        assertThat(isValidCookie).isTrue();

    }

    @DisplayName("만료 시간이 지난 경우 백준 쿠키는 Valid하지 않다.")
    @Test
    void validateCookieWhenExpired() {
        // given
        String 쿠키 = "testCookie";
        Member 사용자 = TestMemberFactory.createMember();
        LocalDateTime 등록된_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(등록된_시간)
                .build();

        LocalDateTime 만료된_시간 = LocalDateTime.of(2021, 1, 1, 6, 0);

        // when
        final boolean validCookie = 백준_사용자_쿠키.isValidCookie(만료된_시간);

        // then
        assertThat(validCookie).isFalse();

    }

}