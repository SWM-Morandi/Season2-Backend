package kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonCookie;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BaekjoonMemberCookieRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BaekjoonMemberCookieRepository baekjoonMemberCookieRepository;

    @DisplayName("사용자의 Id로 백준 쿠키를 찾을 수 있다.")
    @Test
    void findBaekjoonMemberCookieByMemberId() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        String 쿠키 = "dummyCookie";

        BaekjoonMemberCookie 백준_사용자_쿠키 = BaekjoonMemberCookie.builder()
                .member(사용자)
                .cookie(쿠키)
                .nowDateTime(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build();

        memberRepository.save(사용자);
        baekjoonMemberCookieRepository.save(백준_사용자_쿠키);

        // when
        Optional<BaekjoonMemberCookie> maybeBaekjoonMemberCookie = baekjoonMemberCookieRepository.findBaekjoonMemberCookieByMember_MemberId(사용자.getMemberId());

        // then
        assertThat(maybeBaekjoonMemberCookie).isPresent()
                .get()
                .extracting("baekjoonCookie.value").isEqualTo("dummyCookie");

    }


}