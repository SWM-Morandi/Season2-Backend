package kr.co.morandi.backend.member_management.infrastructure.config.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kr.co.morandi.backend.member_management.domain.service.security.OAuthUserDetailsService;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils.SecretKeyProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationProvider {

    private final OAuthUserDetailsService oAuthUserDetailsService;

    private final SecretKeyProvider secretKeyProvider;
    public void setAuthentication(String accessToken) {
        Authentication authentication = getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private Authentication getAuthentication(String accessToken) {
        Long memberId = getMemberIdFromToken(accessToken);
        UserDetails userDetails = oAuthUserDetailsService.loadUserByUsername(memberId.toString());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    private Long getMemberIdFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKeyProvider.getPublicKey())
                .build()
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return Long.parseLong(claims.getSubject());
    }
}
