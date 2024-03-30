package kr.co.morandi.backend.member_management.domain.model.oauth;

public interface UserDto {
    SocialType getSocialType();
    String getEmail();
    String getPicture();
}
