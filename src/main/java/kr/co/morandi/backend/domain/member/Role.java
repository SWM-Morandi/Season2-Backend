package kr.co.morandi.backend.domain.member;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    private String key;
    Role(String key) {
        this.key = key;
    }
}
