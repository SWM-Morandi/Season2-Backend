package kr.co.morandi.backend.member_management.infrastructure.oauth;

import kr.co.morandi.backend.member_management.infrastructure.oauth.constants.SocialType;

public interface OAuthUserInfo {
    SocialType getType();
    String getEmail();
    String getPicture();
}
