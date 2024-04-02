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
    private static int COOKIE_AGE = 24 * 60 * 60;
    @Override
    public Cookie generateLoginCookie(String type, String authenticationCode) {
        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        String oAuthAccessToken = oAuthService.getAccessToken(authenticationCode);
        UserDto userDto = oAuthService.getUserInfo(oAuthAccessToken);
        TokenDto tokenDto = memberLoginService.loginMember(userDto);

        String accessToken = tokenDto.getAccessToken();
        Cookie jwtCookie = getCookie(accessToken);
        return jwtCookie;
    }
    private static Cookie getCookie(String accessToken) {
        Cookie jwtCookie = new Cookie("accessToken", accessToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(COOKIE_AGE);
        return jwtCookie;
    }
}
