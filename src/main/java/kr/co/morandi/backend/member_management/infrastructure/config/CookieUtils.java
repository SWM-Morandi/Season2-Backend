package kr.co.morandi.backend.member_management.infrastructure.config;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.infrastructure.oauth.constants.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

    @Value("${oauth2.cookie.domain}")
    private String domain;

    @Value("${oauth2.cookie.path}")
    private String path;
    public Cookie getCookie(TokenType type, String value, Integer age) {
        Cookie cookie = new Cookie(type.name(), value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(age);
        cookie.setDomain(domain);
        cookie.setPath(path);
        return cookie;
    }
}
