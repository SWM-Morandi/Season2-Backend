package kr.co.morandi.backend.member_management.infrastructure.security.filter.oauth;

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


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        /*
         * 다음 필터인 JwtAuthenticationFilter에서 발생한 예외를 처리하기 위해 필터를 실행한다.
         * */
        try {
            filterChain.doFilter(request, response);
        } catch (MorandiException e) {
            /*
            * JwtAuthenticationFilter에서 발생한 예외가 인증 오류인 경우, refreshToken을 제거하고 로그인 페이지로 리다이렉트한다.
            * */
            if (isAuthError(e)) {
                Cookie cookie = cookieUtils.removeCookie(REFRESH_TOKEN,null);
                response.addCookie(cookie);
            }

            /*
            * JwtAuthenticationFilter에서 발생한 예외가 인증 오류가 아닌 경우, 예외에 해당하는 오류 응답을 반환한다.
             */
            setErrorResponse(response, e.getErrorCode());
        } catch (Exception e) {
            /*
            * JwtAuthenticationFilter에서 발생한 예외가 MorandiException이 아닌 경우, 알 수 없는 오류 응답을 반환한다.
            * */
            setErrorResponse(response, OAuthErrorCode.UNKNOWN_ERROR);
        }
    }
    private boolean isAuthError(MorandiException e) {
        return e.getErrorCode().getHttpStatus() == (HttpStatus.UNAUTHORIZED) || e.getErrorCode().getHttpStatus() == HttpStatus.FORBIDDEN;
    }
    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode)    {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("IOException occurred while writing error response", e);
        }
    }

}
