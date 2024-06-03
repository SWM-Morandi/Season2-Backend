package kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie;

import jakarta.persistence.*;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaekjoonMemberCookie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baekjoonCookieId;

    @Embedded
    private BaekjoonCookie baekjoonCookie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private BaekjoonMemberCookie(BaekjoonCookie baekjoonCookie, Member member) {
        this.baekjoonCookie = baekjoonCookie;
        this.member = member;
    }
}
