package kr.co.morandi.backend.member_management.application.service;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.application.port.in.oauth.AuthenticationUseCase;
import kr.co.morandi.backend.member_management.domain.model.oauth.OAuthUserInfo;
import kr.co.morandi.backend.member_management.domain.service.oauth.OAuthServiceFactory;
import kr.co.morandi.backend.member_management.domain.model.oauth.response.AuthenticationToken;
import kr.co.morandi.backend.member_management.domain.service.member.MemberLoginService;
import kr.co.morandi.backend.member_management.domain.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements AuthenticationUseCase {

    private final OAuthServiceFactory oAuthServiceFactory;

    private final MemberLoginService memberLoginService;
    private static int COOKIE_AGE = 60 * 60 * 24;

    @Value("${oauth2.cookie.domain}")
    private String domain;

    @Value("${oauth2.cookie.path}")
    private String path;
    @Override
    public AuthenticationToken getAuthenticationToken(String type, String authenticationCode) {
        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        String oAuthAccessToken = oAuthService.getAccessToken(authenticationCode);
        OAuthUserInfo oAuthUserInfo = oAuthService.getUserInfo(oAuthAccessToken);
        AuthenticationToken authenticationToken = memberLoginService.loginMember(oAuthUserInfo);
        return authenticationToken;
    }
    @Override
    public Cookie getCookie(String refreshToken) {
        Cookie jwtCookie = new Cookie("refreshToken", refreshToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain(domain);
        jwtCookie.setPath(path);
        jwtCookie.setMaxAge(COOKIE_AGE);
        return jwtCookie;
    }
}
