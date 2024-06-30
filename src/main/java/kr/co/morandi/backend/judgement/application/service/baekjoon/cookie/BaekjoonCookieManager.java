package kr.co.morandi.backend.judgement.application.service.baekjoon.cookie;


import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.BaekjoonCookieErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonCookie;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonGlobalCookie;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonGlobalCookieRepository;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonMemberCookieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaekjoonCookieManager {

    private final BaekjoonMemberCookieRepository memberCookieRepository;
    private final BaekjoonGlobalCookieRepository globalCookieRepository;

    public String getCurrentMemberCookie(final Long memberId, final LocalDateTime now) {
        return memberCookieRepository.findBaekjoonMemberCookieByMember_MemberId(memberId)
                .map(cookie -> this.getValidCookieValue(cookie, now))
                .orElseGet(() -> this.getGlobalCookie(now));
        // 사용자 쿠키가 유효하지 않으면 임시방편으로 글로벌 쿠키를 가져와서 채점하도록 유도함

        // TODO 사용자 쿠키를 재발급하도록 어떻게 알려줄 지
    }
    private String getValidCookieValue(BaekjoonMemberCookie memberCookie, LocalDateTime nowDateTime) {
        if(memberCookie.isValidCookie(nowDateTime)) {
            return memberCookie.getBaekjoonCookie()
                    .getValue();
        }
        log.warn("Member cookie가 유효하지 않습니다. memberId: {}", memberCookie.getMember().getMemberId());
        return getGlobalCookie(nowDateTime);
    }

    private String getGlobalCookie(LocalDateTime nowDateTime) {
        List<BaekjoonGlobalCookie> validCookies = globalCookieRepository.findValidGlobalCookies(nowDateTime);
        if (validCookies.isEmpty()) {
            log.warn("Global cookie가 존재하지 않습니다.");
            throw new MorandiException(BaekjoonCookieErrorCode.NOT_EXIST_GLOBAL_COOKIE);
        }

        Collections.shuffle(validCookies);
        // 등록된 쿠키가 여러 개일 때, 여러 쿠키가 균일하게 사용되도록 하기 위해

        return validCookies.stream()
                .filter(cookie -> cookie.isValidCookie(nowDateTime))
                .map(BaekjoonGlobalCookie::getBaekjoonCookie)
                .map(BaekjoonCookie::getValue)
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Global cookie가 존재하지 않습니다.");
                    return new MorandiException(BaekjoonCookieErrorCode.NOT_EXIST_GLOBAL_COOKIE);
                });
    }
}
