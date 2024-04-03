package kr.co.morandi.backend.member_management.domain.model.oauth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthenticationToken {

    private String accessToken;

    private String refreshToken;
}

