package kr.co.morandi.backend.member_management.application.config.oauth;

import io.jsonwebtoken.Jwts;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.SecurityConstants;
import kr.co.morandi.backend.member_management.domain.model.oauth.TokenDto;
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
    public TokenDto getTokens(Member member) {
        String accessToken = generateAccessToken(member.getMemberId(), "USER");
        String refreshToken = generateRefreshToken(member.getMemberId());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    private String generateAccessToken(Long id, String role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + securityConstants.JWT_EXPIRATION);
        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role);
    }
    private String generateRefreshToken(Long id) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn
                = new Date(issuedAt.getTime() + securityConstants.REFRESH_TOKEN_EXPIRATION);
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn);
    }
    private String buildAccessToken(Long id, Date issuedAt, Date accessTokenExpiresIn, String role) {
        final PrivateKey encodedKey = getPrivateKey();
        return Jwts.builder()
                .setIssuer("MORANDI")
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim("type", "access_token")
                .claim("role", role)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }
    private String buildRefreshToken(Long id, Date issuedAt, Date accessTokenExpiresIn) {
        final PrivateKey encodedKey = getPrivateKey();
        return Jwts.builder()
                .setIssuer("MORANDI")
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim("type", "refresh_token")
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }
    private PrivateKey getPrivateKey() {
        return securityConstants.getPrivateKey();
    }
    private PublicKey getPublicKey() {
        return securityConstants.getPublicKey();
    }
}
