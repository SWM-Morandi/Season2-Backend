package kr.co.morandi.backend.member_management.application.service;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.application.port.in.oauth.AuthenticationUseCase;
import kr.co.morandi.backend.member_management.domain.model.oauth.OAuthUserInfo;
import kr.co.morandi.backend.member_management.domain.service.oauth.OAuthServiceFactory;
import kr.co.morandi.backend.member_management.domain.model.oauth.response.AuthenticationToken;
import kr.co.morandi.backend.member_management.domain.service.member.MemberLoginService;
import kr.co.morandi.backend.member_management.domain.service.oauth.OAuthService;
import kr.co.morandi.backend.member_management.infrastructure.config.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements AuthenticationUseCase {

    private final OAuthServiceFactory oAuthServiceFactory;

    private final MemberLoginService memberLoginService;
    @Override
    public AuthenticationToken getAuthenticationToken(String type, String authenticationCode) {
        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        String oAuthAccessToken = oAuthService.getAccessToken(authenticationCode);
        OAuthUserInfo oAuthUserInfo = oAuthService.getUserInfo(oAuthAccessToken);
        AuthenticationToken authenticationToken = memberLoginService.loginMember(oAuthUserInfo);
        return authenticationToken;
    }
}
