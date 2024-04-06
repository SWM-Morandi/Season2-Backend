package kr.co.morandi.backend.member_management.infrastructure.config.oauth;

import io.jsonwebtoken.*;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.OAuthErrorCode;
import kr.co.morandi.backend.member_management.domain.model.oauth.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtValidator {

    private final SecurityConstants securityConstants;

    public boolean validateToken(String accessToken) {
        return parseTokenToJws(accessToken).isPresent();
    }
    private Jws<Claims> getJws(String accessToken) {
        return parseTokenToJws(accessToken).orElseThrow(
                () -> new MorandiException(OAuthErrorCode.INVALID_TOKEN));
    }
    public Long getUserIdFromToken(String accessToken) {
        Claims claims = getJws(accessToken).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private Optional<Jws<Claims>> parseTokenToJws(String accessToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(accessToken);
            return Optional.of(claimsJws);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException
                 | UnsupportedJwtException | IllegalArgumentException e ) {
            throw new MorandiException(OAuthErrorCode.INVALID_TOKEN);
        }
    }
    private PublicKey getPublicKey() {
        return securityConstants.getPublicKey();
    }
}