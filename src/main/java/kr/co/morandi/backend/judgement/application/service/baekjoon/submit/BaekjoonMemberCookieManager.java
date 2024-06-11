package kr.co.morandi.backend.judgement.application.service.baekjoon.submit;


import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonCookie;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.cookie.BaekjoonMemberCookie;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonGlobalCookieRepository;
import kr.co.morandi.backend.judgement.infrastructure.persistence.baekjoon.BaekjoonMemberCookieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaekjoonMemberCookieManager {

    private final BaekjoonMemberCookieRepository memberCookieRepository;
    private final BaekjoonGlobalCookieRepository globalCookieRepository;

    public String getCurrentMemberCookie(final Long memberId) {

        final Optional<BaekjoonMemberCookie> cookie =
                memberCookieRepository.findBaekjoonMemberCookieByMember_MemberId(memberId);

        if (cookie.isPresent()) {
            final BaekjoonCookie baekjoonCookie = cookie.get()
                    .getBaekjoonCookie();

            if(baekjoonCookie.isValidCookie(LocalDateTime.now())) {
                return baekjoonCookie.getValue();
            }
        }
        // 쿠키를 가져오는 로직을 구현합니다.
//        return globalCookieReposito
        return "dummyCookie";
    }

}
