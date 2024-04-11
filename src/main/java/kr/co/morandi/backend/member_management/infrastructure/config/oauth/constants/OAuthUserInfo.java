package kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants;

import kr.co.morandi.backend.member_management.domain.model.member.SocialType;

public interface OAuthUserInfo {
    SocialType getType();
    String getEmail();
    String getPicture();
}
