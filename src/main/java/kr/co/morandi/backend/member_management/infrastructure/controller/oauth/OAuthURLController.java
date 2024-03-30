package kr.co.morandi.backend.member_management.infrastructure.controller.oauth;

import kr.co.morandi.backend.member_management.infrastructure.config.oauth.OAuthConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/oauths")
@RequiredArgsConstructor
public class OAuthURLController {

    private final OAuthConstants oAuthConstants;
    @GetMapping("/google")
    public String googleRedirect() {
        return "redirect:" + oAuthConstants.GOOGLE_REDIRECT_URL;
    }
}
