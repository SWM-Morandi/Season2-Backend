package kr.co.morandi.backend.domain.member.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.morandi.backend.domain.member.oauth.UserDto;

public interface OAuthService {
    String getType();
    String getAccessToken(String authorizationCode) throws JsonProcessingException;
    UserDto getUserInfo(String accessToken) throws JsonProcessingException;
}
