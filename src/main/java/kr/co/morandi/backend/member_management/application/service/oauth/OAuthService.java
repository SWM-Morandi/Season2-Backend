package kr.co.morandi.backend.member_management.application.service.oauth;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.OAuthUserInfo;

public interface OAuthService {
    String getType();
    String getAccessToken(String authorizationCode);
    OAuthUserInfo getUserInfo(String accessToken);
}
