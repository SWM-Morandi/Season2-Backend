package kr.co.morandi.backend.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
