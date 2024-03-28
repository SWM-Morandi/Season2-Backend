package kr.co.morandi.backend.domain.member;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.member.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("회원을 생성한다")
    @Test
    void createMember() {
        // given
        String nickname = "test";
        String email = "test@test.com";
        SocialType socialType = GOOGLE;
        String profileImageURL = "testImageUrl";
        String description = "testDescription";

        // when
        Member member = Member.create(nickname, email, socialType, profileImageURL, description);

        // then
        assertThat(member)
                .extracting("nickname", "email", "socialType", "profileImageURL", "description")
                .containsExactly(nickname, email, socialType, profileImageURL, description);
    }



}