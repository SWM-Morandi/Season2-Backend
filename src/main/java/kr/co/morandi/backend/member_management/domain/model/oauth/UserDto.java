package kr.co.morandi.backend.member_management.domain.model.oauth;

import kr.co.morandi.backend.member_management.domain.model.oauth.constants.SocialType;

public interface UserDto {
    SocialType getType();
    String getEmail();
    String getPicture();
}
