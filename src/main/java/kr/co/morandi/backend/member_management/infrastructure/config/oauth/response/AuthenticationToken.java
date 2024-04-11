package kr.co.morandi.backend.member_management.infrastructure.config.oauth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthenticationToken {

    private String accessToken;

    private String refreshToken;
    public static AuthenticationToken create(String accessToken, String refreshToken) {
        return AuthenticationToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

