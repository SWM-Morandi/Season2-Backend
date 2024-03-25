package kr.co.morandi.backend.domain.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final OAuthServiceFactory oAuthServiceFactory;

    private final MemberLoginService memberLoginService;
    public TokenDto login(String type, String authorizationCode) throws JsonProcessingException {
        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        String accessToken = oAuthService.getAccessToken(authorizationCode);
        System.out.println("gogo");
        UserDto userDto = oAuthService.getUserInfo(accessToken);

        return memberLoginService.loginOrRegisterMember(userDto);
    }
    public Cookie getCookie(String accessToken) {
        Cookie jwtCookie = new Cookie("accessToken", accessToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("morandi.co.kr");
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        return jwtCookie;
    }
}
