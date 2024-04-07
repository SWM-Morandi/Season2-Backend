package kr.co.morandi.backend.member_management.infrastructure.filter.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.OAuthErrorCode;
import kr.co.morandi.backend.member_management.application.config.oauth.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (jwtProvider.isIgnoredURI(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtProvider.getJwtFromRequest(request);
        String refreshToken = getRefreshToken(request.getCookies());

        if (jwtProvider.validateToken(accessToken)) {
            setAuthentication(accessToken);
            filterChain.doFilter(request, response);
        } else if (jwtProvider.validateToken(refreshToken)) {
            accessToken = jwtProvider.reissueAccessToken(refreshToken);
            setAuthentication(accessToken);
            filterChain.doFilter(request, response);
        } else {
            throw new MorandiException(OAuthErrorCode.EXPIRED_TOKEN);
        }
    }
    private void setAuthentication(String accessToken) {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private String getRefreshToken(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken"))
                return cookie.getValue();
        }
        return null;
    }
}
