package kr.co.morandi.backend.member_management.infrastructure.config.oauth;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuthConstants {

    @Value("${oauth2.google.client-id}")
    public String googleClientId;

    @Value("${oauth2.google.redirect-url}")
    public String googleRedirectUrl;
    public String GOOGLE_REDIRECT_URL;
    public static final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/userinfo/v2/me";
    @PostConstruct
    public void init() {
        GOOGLE_REDIRECT_URL = "https://accounts.google.com/o/oauth2/v2/auth?"
                + "scope=https://www.googleapis.com/auth/userinfo.email&"
                + "access_type=offline&"
                + "include_granted_scopes=true&"
                + "response_type=code&"
                + "state=state_parameter_passthrough_value&"
                + "redirect_uri=" + googleRedirectUrl + "&"
                + "client_id="+ googleClientId;
    }
}
