package kr.co.morandi.backend.member_management.application.service;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.application.port.in.oauth.LoginUseCase;
import kr.co.morandi.backend.member_management.domain.model.oauth.OAuthServiceFactory;
import kr.co.morandi.backend.member_management.domain.model.oauth.TokenDto;
import kr.co.morandi.backend.member_management.domain.model.oauth.UserDto;
import kr.co.morandi.backend.member_management.domain.service.member.MemberLoginService;
import kr.co.morandi.backend.member_management.domain.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final OAuthServiceFactory oAuthServiceFactory;

    private final MemberLoginService memberLoginService;
    @Override
    public TokenDto login(String type, String authenticationCode) {
        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        String accessToken = oAuthService.getAccessToken(authenticationCode);
        UserDto userDto = oAuthService.getUserInfo(accessToken);
        return memberLoginService.loginMember(userDto);
    }
    public Cookie getCookie(String accessToken) {
        Cookie jwtCookie = new Cookie("accessToken", accessToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        return jwtCookie;
    }
}
