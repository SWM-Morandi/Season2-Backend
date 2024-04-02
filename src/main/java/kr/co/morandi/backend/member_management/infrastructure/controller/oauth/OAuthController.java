package kr.co.morandi.backend.member_management.infrastructure.controller.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.member_management.application.port.in.oauth.LoginUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauths")
@Slf4j
public class OAuthController {

    private final LoginUseCase loginUseCase;

    @Value("${morandi.redirect-url}")
    private String redirectUrl;
    @GetMapping("/{type}/callback")
    public ResponseEntity<String> OAuthLogin(@PathVariable String type,
                                             @RequestParam String code,
                                             HttpServletResponse response) {
        response.addCookie(loginUseCase.generateLoginCookie(type, code));
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}
