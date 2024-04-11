package kr.co.morandi.backend.member_management.infrastructure.filter.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import kr.co.morandi.backend.common.exception.response.ErrorResponse;
import kr.co.morandi.backend.member_management.infrastructure.config.cookie.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static kr.co.morandi.backend.member_management.infrastructure.config.jwt.constants.TokenType.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    private final CookieUtils cookieUtils;

    @Value("${oauth2.signup-url}")
    private String signupPath;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MorandiException e) {
            if (isAuthError(e)) {
                Cookie cookie = cookieUtils.getCookie(REFRESH_TOKEN, null,0);
                response.addCookie(cookie);
                response.sendRedirect(signupPath);
            }
            setErrorResponse(response, e.getErrorCode());
        } catch (Exception e) {
            setErrorResponse(response, OAuthErrorCode.UNKNOWN_ERROR);
        }
    }
    private boolean isAuthError(MorandiException e) {
        return e.getErrorCode().getHttpStatus() == (HttpStatus.UNAUTHORIZED);
    }
    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode)    {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = makeErrorResponse(errorCode);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}
