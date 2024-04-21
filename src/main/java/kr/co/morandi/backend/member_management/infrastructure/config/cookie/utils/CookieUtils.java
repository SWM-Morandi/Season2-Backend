package kr.co.morandi.backend.member_management.infrastructure.config.cookie.utils;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.constants.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${oauth2.cookie.domain}")
    private String domain;

    @Value("${oauth2.cookie.path}")
    private String path;

    private final int COOKIE_AGE = 60 * 60 * 24 * 10;
    private final Integer COOKIE_REMOVE_AGE = 0;

    public Cookie getCookie(TokenType type, String value) {
        Cookie cookie = new Cookie(type.name(), value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_AGE);
        cookie.setDomain(domain);
        cookie.setPath(path);
        return cookie;
    }

    public Cookie removeCookie(TokenType type, String value) {
        Cookie cookie = new Cookie(type.name(), value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_REMOVE_AGE);
        cookie.setDomain(domain);
        cookie.setPath(path);
        return cookie;
    }
}
