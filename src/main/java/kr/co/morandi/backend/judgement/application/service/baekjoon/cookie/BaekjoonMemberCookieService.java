package kr.co.morandi.backend.judgement.application.service.baekjoon.cookie;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.application.request.cookie.BaekjoonMemberCookieServiceRequest;
import kr.co.morandi.backend.judgement.domain.error.BaekjoonCookieErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonMemberCookieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BaekjoonMemberCookieService {

    private final BaekjoonMemberCookieRepository baekjoonMemberCookieRepository;

    public void setMemberBaekjoonCookie(BaekjoonMemberCookieServiceRequest request) {
        final Long memberId = request.memberId();
        final String cookie = request.cookie();
        final LocalDateTime nowDateTime = request.nowDateTime();

        final BaekjoonMemberCookie baekjoonMemberCookie = baekjoonMemberCookieRepository.findBaekjoonMemberCookieByMember_MemberId(memberId)
                .orElseThrow(() -> new MorandiException(BaekjoonCookieErrorCode.BAEKJOON_COOKIE_NOT_FOUND));

        baekjoonMemberCookie.updateCookie(cookie, nowDateTime);
    }
}
