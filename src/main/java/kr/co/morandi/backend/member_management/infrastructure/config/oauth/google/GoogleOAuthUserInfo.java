package kr.co.morandi.backend.member_management.infrastructure.config.oauth.google;

import kr.co.morandi.backend.member_management.infrastructure.config.oauth.OAuthUserInfo;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants.SocialType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleOAuthUserInfo implements OAuthUserInfo {

    private String id;
    private String email;
    private String verified_email;
    private String hd;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
    private SocialType type;

    @Override
    public SocialType getType() {
        return type;
    }
    public void setSocialType(SocialType type) {
        this.type = type;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPicture() {
        return picture;
    }
}
