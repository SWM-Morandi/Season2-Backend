package kr.co.morandi.backend.member_management.domain.model.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class TokenResponseDto {

    public String token_type;

    public String access_token;

    public String id_token;

    public Integer expires_in;

    public String refresh_token;

    public Integer refresh_token_expires_in;

    public String scope;
}
