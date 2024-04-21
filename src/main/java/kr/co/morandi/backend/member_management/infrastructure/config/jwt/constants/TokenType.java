package kr.co.morandi.backend.member_management.infrastructure.config.jwt.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String name;
}
