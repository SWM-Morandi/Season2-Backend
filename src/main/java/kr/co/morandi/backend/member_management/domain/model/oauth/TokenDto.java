package kr.co.morandi.backend.member_management.domain.model.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;
}

