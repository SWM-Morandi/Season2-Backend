package kr.co.morandi.backend.domain.member.controller.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.morandi.backend.domain.member.service.oauth.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauths")
@Slf4j
public class OAuthController {

    private final LoginService loginService;
    @GetMapping("/{type}/callback")
    public ResponseEntity<String> OAuthLogin(@PathVariable String type,
                                              @RequestParam String code,
                                              HttpServletResponse response) throws JsonProcessingException {
        String accessToken = loginService.login(type, code).getAccessToken();
        Cookie jwtCookie = loginService.getCookie(accessToken);
        response.addCookie(jwtCookie);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("https://morandi.co.kr"))
                .build();
    }
}