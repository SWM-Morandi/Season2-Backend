package kr.co.morandi.backend.member_management.domain.service.oauth;
import kr.co.morandi.backend.member_management.domain.model.oauth.OAuthUserInfo;

public interface OAuthService {
    String getType();
    String getAccessToken(String authorizationCode);
    OAuthUserInfo getUserInfo(String accessToken);
}
