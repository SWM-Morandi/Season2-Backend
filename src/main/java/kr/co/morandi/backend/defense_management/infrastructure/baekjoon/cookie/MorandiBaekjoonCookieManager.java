package kr.co.morandi.backend.defense_management.infrastructure.baekjoon.cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class MorandiBaekjoonCookieManager {

    private static final String BAEKJOON_URL = "https://www.acmicpc.net/";
    private static final String LOGIN_URL = "https://www.acmicpc.net/login";
    private final WebClient webClient;

    public String getMorandiManagedBaekjoonCookie() {
        return "dummyCookie";
    }

}
