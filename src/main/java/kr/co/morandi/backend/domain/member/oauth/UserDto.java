package kr.co.morandi.backend.domain.member.oauth;

import kr.co.morandi.backend.domain.member.SocialType;

public interface UserDto {
    SocialType getType();
    String getEmail();
    String getPicture();
}
