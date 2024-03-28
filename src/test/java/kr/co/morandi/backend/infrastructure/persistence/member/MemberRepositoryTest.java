package kr.co.morandi.backend.infrastructure.persistence.member;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("닉네임이 이미 존재하면 true를 반환한다")
    @Test
    void existsByNickname() {
        // given
        String nickname = "test";
        Member member = Member.create(nickname,  "test@test.com", GOOGLE, "testImageUrl", "testDescription");
        memberRepository.save(member);

        // when
        Boolean result = memberRepository.existsByNickname(nickname);

        // then
        assertThat(result).isTrue();
    }
    @DisplayName("닉네임이 이미 존재하지 않으면 false를 반환한다")
    @Test
    void whenNicknameNotExists() {
        // given
        String nickname = "test";

        // when
        Boolean result = memberRepository.existsByNickname(nickname);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("닉네임이 이미 있는데 다른 회원이 사용하려고 하면 예외를 발생시킨다")
    @Test
    void test() {
        // given
        String nickname = "test";
        Member originMember = Member.create(nickname,  "test@test.com", GOOGLE, "testImageUrl", "testDescription");
        memberRepository.save(originMember);

        Member newMember = Member.create(nickname,  "test2@test.com", GOOGLE, "testImageUrl", "testDescription");
        // when & then
        assertThatThrownBy(() -> memberRepository.save(newMember))
                .isInstanceOf(DataIntegrityViolationException.class);

    }
}