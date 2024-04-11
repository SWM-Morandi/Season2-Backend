package kr.co.morandi.backend.member_management.infrastructure.config.oauth.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    public String token_type;

    public String access_token;

    public String id_token;

    public Integer expires_in;

    public String refresh_token;

    public Integer refresh_token_expires_in;

    public String scope;
}
