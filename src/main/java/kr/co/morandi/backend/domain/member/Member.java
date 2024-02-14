package kr.co.morandi.backend.domain.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String nickname;

    private String baekjoonId;

    private String socialEmail;

    @Enumerated(EnumType.STRING)
    private SocialType socialInfo;

    private String profileURL;

    private String description;

    @Builder
    private Member(Long memberId, String nickname, String baekjoonId, String socialEmail, SocialType socialInfo, String profileURL, String description) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.baekjoonId = baekjoonId;
        this.socialEmail = socialEmail;
        this.socialInfo = socialInfo;
        this.profileURL = profileURL;
        this.description = description;
    }
}
