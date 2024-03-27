package kr.co.morandi.backend.domain.member.controller.oauth;

import kr.co.morandi.backend.domain.member.oauth.OAuthConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauths")
@RequiredArgsConstructor
public class OAuthURIController {

    private final OAuthConstants oAuthConstants;
    @GetMapping("/google")
    public String googleRedirect() {
        return "redirect:" + oAuthConstants.GOOGLE_REDIRECT_URL;
    }
}
