package kr.co.morandi.backend.member_management.infrastructure.config.oauth;

import kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants.SocialType;

public interface OAuthUserInfo {
    SocialType getType();
    String getEmail();
    String getPicture();
}
