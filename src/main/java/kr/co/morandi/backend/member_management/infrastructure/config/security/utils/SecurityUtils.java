package kr.co.morandi.backend.member_management.infrastructure.config.security.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        /*
        * 일반적인 JWT 토큰을 사용할 때 발생.
        * */
        if (principal instanceof Long) {
            return (Long) principal;
        }
        /*
        * @WithMockUser를 포함한 테스트나 기타 UserDetails 서비스를 사용할 때 발생한다.
        * */
        if (principal instanceof UserDetails) {
            /*
            *  principal이 UserDetails 타입인 경우, getUsername()에서 사용자 ID를 추출
            *  따라서 @WithMockUser를 사용할 때는 getUsername에 Member ID를 넣어줘야 한다.
            */
            UserDetails userDetails = (UserDetails) principal;
            try {
                return Long.valueOf(userDetails.getUsername());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
