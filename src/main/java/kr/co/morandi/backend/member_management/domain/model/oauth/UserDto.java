package kr.co.morandi.backend.member_management.domain.model.oauth;

public interface UserDto {
    SocialType getType();
    String getEmail();
    String getPicture();
}
