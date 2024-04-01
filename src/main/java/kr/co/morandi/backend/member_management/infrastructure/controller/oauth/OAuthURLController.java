package kr.co.morandi.backend.member_management.infrastructure.controller.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauths")
@RequiredArgsConstructor
public class OAuthURLController {

    @Value("${oauth2.google.redirect-url}")
    private String googleRedirectUrl;
    @GetMapping("/google")
    public String googleRedirect() {
        System.out.println("url : " + googleRedirectUrl);
        return "redirect:" + googleRedirectUrl;
    }
}
