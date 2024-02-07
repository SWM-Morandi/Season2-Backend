package kr.co.morandi.backend.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"), GITHUB("github"), NAVER("naver");

    private final String provider;
}
