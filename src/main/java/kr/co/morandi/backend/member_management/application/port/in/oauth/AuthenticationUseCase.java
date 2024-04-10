package kr.co.morandi.backend.member_management.application.port.in.oauth;

import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.domain.model.oauth.response.AuthenticationToken;

public interface AuthenticationUseCase {
    AuthenticationToken getAuthenticationToken(String type, String authenticationCode);
}
