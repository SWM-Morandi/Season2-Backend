package kr.co.morandi.backend.member_management.application.port.in.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.domain.model.oauth.TokenDto;

public interface LoginUseCase {
    Cookie generateLoginCookie(String type, String authenticationCode);
}
