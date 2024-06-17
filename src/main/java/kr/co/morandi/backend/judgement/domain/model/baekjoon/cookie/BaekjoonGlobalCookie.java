package kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.BaekjoonCookieErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaekjoonGlobalCookie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baekjoonCookieId;

    @Embedded
    private BaekjoonCookie baekjoonCookie;

    private String globalUserId;

    // 백준의 Refresh Token을 저장하는 필드
    private String baekjoonRefreshToken;

    public boolean isValidCookie(LocalDateTime nowDateTime) {
        return baekjoonCookie.isValidCookie(nowDateTime);
    }

    public static BaekjoonGlobalCookie create(BaekjoonCookie baekjoonCookie, String globalUserId, String refreshToken) {
        return new BaekjoonGlobalCookie(baekjoonCookie, globalUserId, refreshToken);
    }

    @Builder
    private BaekjoonGlobalCookie(BaekjoonCookie baekjoonCookie, String globalUserId, String baekjoonRefreshToken) {
        validateGlobalUserId(globalUserId);
        validateBaekjoonRefreshToken(baekjoonRefreshToken);
        this.baekjoonCookie = baekjoonCookie;
        this.globalUserId = globalUserId;
        this.baekjoonRefreshToken = baekjoonRefreshToken;
    }

    private void validateGlobalUserId(String globalUserId) {
        if (globalUserId == null || globalUserId.trim().isEmpty()) {
            throw new MorandiException(BaekjoonCookieErrorCode.INVALID_GLOBAL_USER_ID);
        }
    }

    private void validateBaekjoonRefreshToken(String baekjoonRefreshToken) {
        if (baekjoonRefreshToken == null || baekjoonRefreshToken.trim().isEmpty()) {
            throw new MorandiException(BaekjoonCookieErrorCode.INVALID_BAEKJOON_REFRESH_TOKEN);
        }
    }
}
