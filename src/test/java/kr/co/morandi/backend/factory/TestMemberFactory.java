package kr.co.morandi.backend.factory;

import kr.co.morandi.backend.member_management.domain.model.member.Member;

public class TestMemberFactory {
    public static Member createMember() {
        return Member.builder()
                .nickname("nickname")
                .baekjoonId("baekjoonId")
                .email("email")
                .build();
    }
}

