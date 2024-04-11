package kr.co.morandi.backend.member_management.infrastructure.filter.oauth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils.JwtValidator;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils.JwtProvider;
import kr.co.morandi.backend.member_management.infrastructure.config.security.utils.AuthenticationProvider;
import kr.co.morandi.backend.member_management.infrastructure.config.security.utils.IgnoredURIManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final JwtValidator jwtValidator;

    private final AuthenticationProvider authenticationProvider;

    private final IgnoredURIManager isIgnoredURIManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isIgnoredURIManager.isIgnoredURI(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtProvider.getAccessToken(request);
        String refreshToken = jwtProvider.getRefreshToken(request);

        if (jwtValidator.validateToken(accessToken)) { // accessToken이 유효할 경우
            authenticationProvider.setAuthentication(accessToken);
            filterChain.doFilter(request, response);
        }
        if (jwtValidator.validateToken(refreshToken)) { // refreshToken이 유효할 경우
            accessToken = jwtProvider.reissueAccessToken(refreshToken);
            authenticationProvider.setAuthentication(accessToken);
            filterChain.doFilter(request, response);
        }
        throw new MorandiException(OAuthErrorCode.EXPIRED_TOKEN);
    }

}
