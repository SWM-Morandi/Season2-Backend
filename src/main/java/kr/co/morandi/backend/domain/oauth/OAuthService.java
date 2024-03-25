package kr.co.morandi.backend.domain.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OAuthService {
    String getType();
    String getAccessToken(String authorizationCode) throws JsonProcessingException;
    UserDto getUserInfo(String accessToken) throws JsonProcessingException;
}
