package kr.co.morandi.backend.member_management.infrastructure.controller.oauth;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.member_management.application.port.in.oauth.AuthenticationUseCase;
import kr.co.morandi.backend.member_management.infrastructure.oauth.response.AuthenticationToken;
import kr.co.morandi.backend.member_management.infrastructure.config.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static kr.co.morandi.backend.member_management.infrastructure.oauth.constants.TokenType.REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauths")
@Slf4j
public class OAuthController {

    private final AuthenticationUseCase authenticationUseCase;

    private final CookieUtils cookieUtils;
    private int COOKIE_AGE = 60 * 60 * 24 * 10;

    @Value("${morandi.redirect-url}")
    private String redirectUrl;
    @GetMapping("/{type}/callback")
    public ResponseEntity<String> OAuthLogin(@PathVariable String type,
                                             @RequestParam String code,
                                             HttpServletResponse response) {
        AuthenticationToken authenticationToken = authenticationUseCase.getAuthenticationToken(type, code);
        response.setHeader("Authorization", "Bearer " + authenticationToken.getAccessToken());
        response.addCookie(cookieUtils.getCookie(REFRESH_TOKEN, authenticationToken.getRefreshToken(), COOKIE_AGE));
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}
