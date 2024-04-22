package kr.co.morandi.backend.member_management.infrastructure.config.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getName().equals("anonymousUser")) {
            return null;
        }
        return Long.valueOf(authentication.getName());
    }
}
