package kr.co.morandi.backend.member_management.infrastructure.filter.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.AuthErrorCode;
import kr.co.morandi.backend.common.exception.errorcode.ErrorCode;
import kr.co.morandi.backend.common.exception.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Value("${oauth2.cookie.domain}")
    private String domain;

    @Value("${oauth2.cookie.path}")
    private String path;

    @Value("${oauth2.signup-url}")
    private String signupPath;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MorandiException e) {
            if (e.getErrorCode().getHttpStatus() == (HttpStatus.UNAUTHORIZED)) {
                Cookie cookie = new Cookie("accessToken", null);
                cookie.setMaxAge(0);
                cookie.setDomain(domain);
                cookie.setPath(path);
                response.addCookie(cookie);
                response.sendRedirect(signupPath);
            }
            setErrorResponse(response, e.getErrorCode());
        } catch (Exception e) {
            setErrorResponse(response, AuthErrorCode.UNKNOWN_ERROR);
        }
    }
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
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
}
