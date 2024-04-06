package kr.co.morandi.backend.member_management.application.config.oauth;

import io.jsonwebtoken.Jwts;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.constants.Role;
import kr.co.morandi.backend.member_management.domain.model.oauth.constants.TokenType;
import kr.co.morandi.backend.member_management.domain.model.oauth.security.SecurityConstants;
import kr.co.morandi.backend.member_management.domain.model.oauth.response.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtProvider {

    private final SecurityConstants securityConstants;
    public AuthenticationToken getAuthenticationToken(Member member) {
        String accessToken = generateAccessToken(member.getMemberId(), Role.USER);
        String refreshToken = generateRefreshToken(member.getMemberId(), Role.USER);
        return AuthenticationToken.create(accessToken, refreshToken);
    }
    private String generateAccessToken(Long id, Role role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + securityConstants.ACCESS_TOKEN_EXPIRATION);
        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role);
    }
    private String generateRefreshToken(Long id, Role role) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + securityConstants.REFRESH_TOKEN_EXPIRATION);
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn, role);
    }
    private String buildAccessToken(Long id, Date issuedAt, Date expiresIn, Role role) {
        final PrivateKey encodedKey = getPrivateKey();
        return jwtCreate(id, issuedAt, expiresIn, role, encodedKey, TokenType.ACCESS_TOKEN);
    }
    private String buildRefreshToken(Long id, Date issuedAt, Date expiresIn, Role role) {
        final PrivateKey encodedKey = getPrivateKey();
        return jwtCreate(id, issuedAt, expiresIn, role, encodedKey, TokenType.REFRESH_TOKEN);
    }
    private String jwtCreate(Long id, Date issuedAt, Date expiresIn, Role role,
                             PrivateKey encodedKey, TokenType tokenType) {
        return Jwts.builder()
                .setIssuer("MORANDI")
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim("type", tokenType)
                .claim("role", role)
                .setExpiration(expiresIn)
                .signWith(encodedKey)
                .compact();
    }
    private PrivateKey getPrivateKey() {
        return securityConstants.getPrivateKey();
    }
}
