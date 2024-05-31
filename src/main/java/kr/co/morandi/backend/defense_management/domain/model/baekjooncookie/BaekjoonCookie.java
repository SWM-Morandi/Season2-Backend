package kr.co.morandi.backend.defense_management.domain.model.baekjooncookie;

import jakarta.persistence.Embeddable;
import kr.co.morandi.backend.common.exception.MorandiException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaekjoonCookie {

    private String cookie;
    private LocalDateTime cookieExpiredAt;

    public boolean isValidCookie(LocalDateTime now) {
        return now.isBefore(cookieExpiredAt);
    }

    public static BaekjoonCookie of(String cookie, LocalDateTime now) {
        return BaekjoonCookie.builder()
                .cookie(cookie)
                .now(now)
                .build();
    }

    private void validateCookie(String cookie) {
        if (cookie == null || cookie.trim().isEmpty()) {
            throw new MorandiException(BaekjoonCookieErrorCode.INVALID_COOKIE_VALUE);
        }
    }

    private LocalDateTime calculateCookieExpiredAt(LocalDateTime cookieCreatedAt) {
        return cookieCreatedAt.plusHours(6);
    }

    @Builder
    private BaekjoonCookie(String cookie, LocalDateTime now) {
        validateCookie(cookie);
        this.cookie = cookie;
        this.cookieExpiredAt = calculateCookieExpiredAt(now);
    }
}
