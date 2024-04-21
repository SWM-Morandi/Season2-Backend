package kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private final SecretKeyProvider secretKeyProvider;
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token))
            throw new MorandiException(OAuthErrorCode.INVALID_TOKEN);
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKeyProvider.getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            throw new MorandiException(OAuthErrorCode.INVALID_TOKEN);
        }
    }
}
