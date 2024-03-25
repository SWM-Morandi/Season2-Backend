package kr.co.morandi.backend.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
    public String token_type;

    public String access_token;

    public String id_token;

    public Integer expires_in;

    public String refresh_token;

    public Integer refresh_token_expires_in;

    public String scope;
}
