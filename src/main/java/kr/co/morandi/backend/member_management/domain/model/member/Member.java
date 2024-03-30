package kr.co.morandi.backend.member_management.domain.model.member;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.member_management.domain.model.oauth.SocialType;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String nickname;

    private String baekjoonId;

    private String email;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String profileImageURL;

    private String description;

    @Builder
    private Member(String nickname, String baekjoonId, String email, SocialType socialType, String profileImageURL, String description) {
        this.nickname = nickname;
        this.baekjoonId = baekjoonId;
        this.email = email;
        this.socialType = socialType;
        this.profileImageURL = profileImageURL;
        this.description = description;
    }

    public static Member create(String nickname, String email, SocialType socialType, String profileImageURL, String description) {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .socialType(socialType)
                .profileImageURL(profileImageURL)
                .description(description)
                .build();
    }
}
