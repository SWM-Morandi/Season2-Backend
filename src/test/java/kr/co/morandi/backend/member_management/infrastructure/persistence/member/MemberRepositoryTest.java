package kr.co.morandi.backend.member_management.infrastructure.persistence.member;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.member.SocialType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("사용자 ID로 사용자와 쿠키를 조인해서 조회할 수 있다.")
    @Test
    void findMemberJoinFetchCookie() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        사용자.saveBaekjoonCookie("dummyCookie", LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        final Member 저장된_사용자 = memberRepository.save(사용자);


        // when
        Optional<Member> 찾은_사용자 = memberRepository.findMemberJoinFetchCookie(저장된_사용자.getMemberId());

        // then
        assertThat(찾은_사용자).isPresent()
                .get()
                .extracting("baekjoonMemberCookie.baekjoonCookie.value", "baekjoonMemberCookie.baekjoonCookie.expiredAt")
                .contains("dummyCookie", LocalDateTime.of(2021, 1, 1, 0, 0, 0).plusHours(6));
    }


    @DisplayName("닉네임이 이미 존재하면 true를 반환한다")
    @Test
    void existsByNickname() {
        // given
        String nickname = "test";
        Member member = Member.create(nickname,  "test@test.com", SocialType.GOOGLE, "testImageUrl", "testDescription");
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
        Member originMember = Member.create(nickname,  "test@test.com", SocialType.GOOGLE, "testImageUrl", "testDescription");
        memberRepository.save(originMember);

        Member newMember = Member.create(nickname,  "test2@test.com", SocialType.GOOGLE, "testImageUrl", "testDescription");
        // when & then
        assertThatThrownBy(() -> memberRepository.save(newMember))
                .isInstanceOf(DataIntegrityViolationException.class);

    }
}