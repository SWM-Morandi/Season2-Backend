package kr.co.morandi.backend.member_management.application.port.in.oauth;

import jakarta.servlet.http.Cookie;

public interface LoginUseCase {
    Cookie generateLoginCookie(String type, String authenticationCode);
}
