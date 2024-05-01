package kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.member.Role;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.constants.TokenType;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.response.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import java.security.PrivateKey;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtProvider {

    private final Long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 3 * 1000L; // 3 hours
    private final Long REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 7 * 1000L; // 7 days

    private final SecretKeyProvider secretKeyProvider;

    public AuthenticationToken getAuthenticationToken(Member member) {
        String accessToken = generateAccessToken(member.getMemberId(), Role.USER);
        String refreshToken = generateRefreshToken(member.getMemberId(), Role.USER);
        return AuthenticationToken.create(accessToken, refreshToken);
    }

    public String parseAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        return null;
    }
    public String parseRefreshToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "REFRESH_TOKEN");
        if(cookie==null)
            return null;
        return cookie.getValue();
    }
    public String reissueAccessToken(String refreshToken) {
        Long memberId = getMemberIdFromToken(refreshToken);

        return generateAccessToken(memberId, Role.USER);
    }

    private String generateAccessToken(Long id, Role role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + ACCESS_TOKEN_EXPIRATION);
        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role);
    }
    private String generateRefreshToken(Long id, Role role) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + REFRESH_TOKEN_EXPIRATION);
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn, role);
    }
    private String buildAccessToken(Long id, Date issuedAt, Date expiresIn, Role role) {
        final PrivateKey encodedKey = secretKeyProvider.getPrivateKey();
        return jwtCreate(id, issuedAt, expiresIn, role, encodedKey, TokenType.ACCESS_TOKEN);
    }
    private String buildRefreshToken(Long id, Date issuedAt, Date expiresIn, Role role) {
        final PrivateKey encodedKey = secretKeyProvider.getPrivateKey();

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
    private Long getMemberIdFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKeyProvider.getPublicKey())
                .build()
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Long.parseLong(claims.getSubject());
    }
}
