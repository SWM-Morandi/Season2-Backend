package kr.co.morandi.backend.member_management.infrastructure.security.filter.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.common.exception.response.ErrorResponse;
import kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils.JwtProvider;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /*
        * AccessToken이 존재하지 않는 경우
        * */
        if(jwtProvider.parseAccessToken(request) == null) {
            response.setStatus(OAuthErrorCode.ACCESS_TOKEN_NOT_FOUND.getHttpStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(OAuthErrorCode.ACCESS_TOKEN_NOT_FOUND)));
            response.getWriter().flush();

            return;
        }
        /*
        * RefreshToken이 존재하지 않는 경우
        * */
        if(jwtProvider.parseRefreshToken(request) == null) {
            response.setStatus(OAuthErrorCode.REFRESH_TOKEN_NOT_FOUND.getHttpStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(OAuthErrorCode.REFRESH_TOKEN_NOT_FOUND)));
            response.getWriter().flush();

            return;
        }

        /*
        * RefreshToken이 만료된 경우
        * */
        response.setStatus(OAuthErrorCode.EXPIRED_TOKEN.getHttpStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(OAuthErrorCode.EXPIRED_TOKEN)));


    }
}
