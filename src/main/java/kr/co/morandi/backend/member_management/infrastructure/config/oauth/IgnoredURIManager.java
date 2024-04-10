package kr.co.morandi.backend.member_management.infrastructure.config.oauth;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IgnoredURIManager {
    private static final String[] IGNORED_URIS = {
        "/oauths/",
        "/swagger-ui/",
        "/v3/api-docs/",
        "/swagger-resources/"
    };
    private String PATTERN_STRING = String.join("|", IGNORED_URIS);
    public Pattern PATTERN = Pattern.compile(PATTERN_STRING);
    public boolean isIgnoredURI(String uri) {
        Matcher matcher = PATTERN.matcher(uri);
        return matcher.find();
    }
}
