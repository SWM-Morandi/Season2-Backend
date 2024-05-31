package kr.co.morandi.backend.defense_management.application.service.judgement.baekjoon;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaekjoonMemberCookieService {

    public String getCurrentMemberCookie() {
        // 쿠키를 가져오는 로직을 구현합니다.
        return "dummyCookie";
    }

}
