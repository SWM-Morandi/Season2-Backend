package kr.co.morandi.backend.member_management.domain.service.oauth;
import kr.co.morandi.backend.member_management.domain.model.oauth.UserDto;

public interface OAuthService {
    String getType();
    String getAccessToken(String authorizationCode);
    UserDto getUserInfo(String accessToken);
}
