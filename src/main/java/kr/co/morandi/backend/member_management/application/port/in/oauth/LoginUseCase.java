package kr.co.morandi.backend.member_management.application.port.in.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import kr.co.morandi.backend.member_management.domain.model.oauth.TokenDto;

public interface LoginUseCase {
    TokenDto login(String type, String authenticationCode);
    Cookie getCookie(String accessToken);
}
