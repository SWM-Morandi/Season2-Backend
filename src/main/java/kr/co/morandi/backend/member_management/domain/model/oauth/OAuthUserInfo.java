package kr.co.morandi.backend.member_management.domain.model.oauth;

import kr.co.morandi.backend.member_management.domain.model.oauth.constants.SocialType;

public interface OAuthUserInfo {
    SocialType getType();
    String getEmail();
    String getPicture();
}
