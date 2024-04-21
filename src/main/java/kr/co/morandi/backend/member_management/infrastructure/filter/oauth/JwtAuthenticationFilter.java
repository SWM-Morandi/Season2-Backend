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

        /*
        * 인증 필요 없는 URI인 경우 필터링하지 않고 바로 다음 필터로 넘어간다.
        * */
        if (isIgnoredURIManager.isIgnoredURI(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtProvider.parseAccessToken(request);
        String refreshToken = jwtProvider.parseRefreshToken(request);

        /*
        * accessToken이 유효한 경우, accessToken을 이용하여 인증을 수행하고 다음 필터로 넘어간다.
        * */
        if (jwtValidator.validateToken(accessToken)) { // accessToken이 유효할 경우
            authenticationProvider.setAuthentication(accessToken);

            filterChain.doFilter(request, response);
            return ;
        }

        /*
        * accessToken의 유효 기간이 만료된 경우, refreshToken을 이용하여 accessToken을 재발급하고 다음 필터로 넘어간다.
        * */
        if (jwtValidator.validateToken(refreshToken)) { // refreshToken이 유효할 경우
            accessToken = jwtProvider.reissueAccessToken(refreshToken);
            response.setHeader("Authorization", "Bearer " + accessToken);

            authenticationProvider.setAuthentication(accessToken);
            filterChain.doFilter(request, response);
            return ;
        }

        /*
        * refreshToken의 유효 기간도 만료된 경우, refreshToken이 만료되었다는 오류를 반환한다.
        * */
        throw new MorandiException(OAuthErrorCode.EXPIRED_TOKEN);
    }

}
