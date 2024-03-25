package kr.co.morandi.backend.domain.oauth;

import kr.co.morandi.backend.domain.member.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserDto implements UserDto {

    private String id;
    private String email;
    private String verified_email;
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPicture() {
        return picture;
    }
}
