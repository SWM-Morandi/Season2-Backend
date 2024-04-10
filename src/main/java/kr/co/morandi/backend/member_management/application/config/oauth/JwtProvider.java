package kr.co.morandi.backend.member_management.application.config.oauth;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.OAuthErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.oauth.constants.Role;
import kr.co.morandi.backend.member_management.infrastructure.oauth.constants.TokenType;
import kr.co.morandi.backend.member_management.infrastructure.security.SecurityConstants;
import kr.co.morandi.backend.member_management.infrastructure.oauth.response.AuthenticationToken;
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
        final PrivateKey encodedKey = securityConstants.getPrivateKey();
        return jwtCreate(id, issuedAt, expiresIn, role, encodedKey, TokenType.ACCESS_TOKEN);
    }
    private String buildRefreshToken(Long id, Date issuedAt, Date expiresIn, Role role) {
        final PrivateKey encodedKey = securityConstants.getPrivateKey();
        String refreshToken = jwtCreate(id, issuedAt, expiresIn, role, encodedKey, TokenType.REFRESH_TOKEN);
        return refreshToken;
    }
    public String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        throw new MorandiException(OAuthErrorCode.TOKEN_NOT_FOUND);
    }
    public String getRefreshToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");
        return cookie.getValue();
    }
    public String reissueAccessToken(String refreshToken) {
        Long memberId = getMemberIdFromToken(refreshToken);
        return generateAccessToken(memberId, Role.USER);
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
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token))
            throw new MorandiException(OAuthErrorCode.INVALID_TOKEN);
        try {
            Jwts.parserBuilder()
                    .setSigningKey(securityConstants.getPublicKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            throw new MorandiException(OAuthErrorCode.INVALID_TOKEN);
        }
    }
    private Long getMemberIdFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(securityConstants.getPublicKey())
                .build()
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Long.parseLong(claims.getSubject());
    }
}
