package kr.co.morandi.backend.member_management.domain.model.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"),
    GITHUB("github"),
    NAVER("naver");

    private final String provider;
}
