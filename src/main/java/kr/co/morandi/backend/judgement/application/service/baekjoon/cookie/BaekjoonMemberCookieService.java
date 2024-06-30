package kr.co.morandi.backend.judgement.application.service.baekjoon.cookie;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.application.request.cookie.BaekjoonMemberCookieServiceRequest;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonMemberCookieRepository;
import kr.co.morandi.backend.member_management.domain.model.error.MemberErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaekjoonMemberCookieService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveMemberBaekjoonCookie(BaekjoonMemberCookieServiceRequest request) {
        final Long memberId = request.memberId();
        final String cookie = request.cookie();
        final LocalDateTime nowDateTime = request.nowDateTime();

        final Member member = memberRepository.findMemberJoinFetchCookie(memberId)
                .orElseThrow(() -> new MorandiException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.saveBaekjoonCookie(cookie, nowDateTime);
    }
}

