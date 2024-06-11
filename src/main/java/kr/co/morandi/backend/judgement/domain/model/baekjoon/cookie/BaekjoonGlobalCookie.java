package kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaekjoonGlobalCookie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baekjoonCookieId;

    @Embedded
    private BaekjoonCookie baekjoonCookie;

    public static BaekjoonGlobalCookie create(BaekjoonCookie baekjoonCookie) {
        return new BaekjoonGlobalCookie(baekjoonCookie);
    }

    private BaekjoonGlobalCookie(BaekjoonCookie baekjoonCookie) {
        this.baekjoonCookie = baekjoonCookie;
    }
}
