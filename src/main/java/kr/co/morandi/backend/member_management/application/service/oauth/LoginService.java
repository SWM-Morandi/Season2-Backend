package kr.co.morandi.backend.member_management.application.service.oauth;

import kr.co.morandi.backend.member_management.application.port.in.oauth.AuthenticationUseCase;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants.OAuthUserInfo;
import kr.co.morandi.backend.member_management.application.model.oauth.OAuthServiceFactory;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.response.AuthenticationToken;
import kr.co.morandi.backend.member_management.application.service.jwt.MemberLoginService;
import lombok.RequiredArgsConstructor;
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
