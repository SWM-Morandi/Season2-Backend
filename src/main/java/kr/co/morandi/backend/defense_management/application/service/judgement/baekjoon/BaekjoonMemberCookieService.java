package kr.co.morandi.backend.defense_management.application.service.judgement.baekjoon;


import kr.co.morandi.backend.defense_management.infrastructure.baekjoon.cookie.MorandiBaekjoonCookieManager;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.baekjoonmembercookie.BaekjoonMemberCookieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaekjoonMemberCookieService {

    private final BaekjoonMemberCookieRepository baekjoonMemberCookieRepository;

    private final MorandiBaekjoonCookieManager morandiBaekjoonCookieManager;


    public String getCurrentMemberCookie() {
        // 쿠키를 가져오는 로직을 구현합니다.
        return "dummyCookie";
    }

}
