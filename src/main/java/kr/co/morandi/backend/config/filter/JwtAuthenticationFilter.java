package kr.co.morandi.backend.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.config.security.JwtProvider;
import kr.co.morandi.backend.config.security.JwtValidator;
import kr.co.morandi.backend.domain.oauth.OAuthUserDetailsService;
import kr.co.morandi.backend.global.exception.MorandiException;
import kr.co.morandi.backend.global.exception.errorcode.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final OAuthUserDetailsService oAuthUserDetailsService;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isIgnoredURI(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = getJwtFromRequest(request);

        if (StringUtils.hasText(accessToken) && jwtValidator.validateToken(accessToken)) {
            Long memberId = jwtValidator.getUserIdFromToken(accessToken);
            UserDetails userDetails = oAuthUserDetailsService.loadUserByUsername(memberId.toString());
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
        else {
            throw new MorandiException(AuthErrorCode.INVALID_TOKEN);
        }
    }
    private boolean isIgnoredURI(String uri) {
        return uri.startsWith("/oauths/") ||
                uri.startsWith("/swagger-ui/") ||
                uri.startsWith("/v3/api-docs/") ||
                uri.startsWith("/swagger-resources/");
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "accessToken");
        if (cookie != null) {
            return cookie.getValue();
        }
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        throw new MorandiException(AuthErrorCode.TOKEN_NOT_FOUND, "토큰이 없습니다.");
    }
}
