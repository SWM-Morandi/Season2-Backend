package kr.co.morandi.backend.member_management.infrastructure.config.oauth;

import io.jsonwebtoken.*;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.AuthErrorCode;
import kr.co.morandi.backend.member_management.domain.model.oauth.SecurityConstants;
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
                () -> new MorandiException(AuthErrorCode.INVALID_TOKEN));
    }
    public Long getUserIdFromToken(String accessToken) {
        Claims claims = getJws(accessToken).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private Optional<Jws<Claims>> parseTokenToJws(String accessToken) {
        if (accessToken == null) {
            throw new MorandiException(AuthErrorCode.INVALID_TOKEN);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(accessToken);
            return Optional.of(claimsJws);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT Token");
            throw new MorandiException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 JWT signature");
            throw new MorandiException(AuthErrorCode.INVALID_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰");
            throw new MorandiException(AuthErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgumentException");

        }
        throw new MorandiException(AuthErrorCode.INVALID_TOKEN);
    }
    private PublicKey getPublicKey() {
        return securityConstants.getPublicKey();
    }
}