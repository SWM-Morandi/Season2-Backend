package kr.co.morandi.backend.judgement.application.service.baekjoon.cookie;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.judgement.application.request.cookie.BaekjoonMemberCookieServiceRequest;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonMemberCookieRepository;
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
class BaekjoonMemberCookieServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BaekjoonMemberCookieService baekjoonMemberCookieService;

    @Autowired
    private BaekjoonMemberCookieRepository baekjoonMemberCookieRepository;

    @DisplayName("이미 쿠키가 저장돼있는 경우에도 쿠키를 갱신할 수 있다.")
    @Test
    void saveMemberBaekjoonCookie_이미_쿠키가_저장돼있는_경우() {
        // given
        LocalDateTime 과거시각 = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        String 과거_쿠키 = "dummyCookie";
        Member 사용자 = TestMemberFactory.createMember();
        사용자.saveBaekjoonCookie(과거_쿠키, 과거시각);
        final Member 저장된_사용자 = memberRepository.save(사용자);

        String 새로운_쿠키 = "newDummyCookie";
        LocalDateTime 현재시각 = LocalDateTime.of(2021, 1, 2, 0, 0, 0);

        final BaekjoonMemberCookieServiceRequest 서비스_요청 = new BaekjoonMemberCookieServiceRequest(새로운_쿠키, 저장된_사용자.getMemberId(), 현재시각);

        // when
        baekjoonMemberCookieService.saveMemberBaekjoonCookie(서비스_요청);

        // then
        final Optional<BaekjoonMemberCookie> 저장된_쿠키 = baekjoonMemberCookieRepository.findBaekjoonMemberCookieByMember_MemberId(저장된_사용자.getMemberId());

        assertThat(저장된_쿠키).isPresent()
                .get()
                .extracting("baekjoonCookie.value", "baekjoonCookie.expiredAt")
                .contains("newDummyCookie", 현재시각.plusHours(6));

    }

    @DisplayName("사용자의 쿠키를 저장할 수 있다.")
    @Test
    void saveMemberBaekjoonCookie() {
        // given
        LocalDateTime 현재시각 = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        String 쿠키 = "dummyCookie";
        Member 사용자 = TestMemberFactory.createMember();

        final Member 저장된_사용자 = memberRepository.save(사용자);

        final BaekjoonMemberCookieServiceRequest 서비스_요청 = new BaekjoonMemberCookieServiceRequest(쿠키, 저장된_사용자.getMemberId(), 현재시각);

        // when
        baekjoonMemberCookieService.saveMemberBaekjoonCookie(서비스_요청);

        // then
        final Optional<BaekjoonMemberCookie> 저장된_쿠키 = baekjoonMemberCookieRepository.findBaekjoonMemberCookieByMember_MemberId(저장된_사용자.getMemberId());

        assertThat(저장된_쿠키).isPresent()
                .get()
                .extracting("baekjoonCookie.value", "baekjoonCookie.expiredAt")
                .contains(쿠키, 현재시각.plusHours(6));
    }

}