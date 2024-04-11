package kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("accessToken"), REFRESH_TOKEN("refreshToken");

    String name;
}
