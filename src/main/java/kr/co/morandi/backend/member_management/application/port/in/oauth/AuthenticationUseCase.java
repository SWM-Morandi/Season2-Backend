package kr.co.morandi.backend.member_management.application.port.in.oauth;

import kr.co.morandi.backend.member_management.infrastructure.oauth.response.AuthenticationToken;

public interface AuthenticationUseCase {
    AuthenticationToken getAuthenticationToken(String type, String authenticationCode);
}
