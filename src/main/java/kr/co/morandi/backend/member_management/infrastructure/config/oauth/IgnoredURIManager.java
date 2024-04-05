package kr.co.morandi.backend.member_management.infrastructure.config.oauth;

import java.util.regex.Pattern;

public class IgnoredURIManager {
    private static final String[] IGNORED_URIS = {
        "/oauths/",
        "/swagger-ui/",
        "/v3/api-docs/",
        "/swagger-resources/"
    };
    private static String PATTERN_STRING = String.join("|", IGNORED_URIS);
    public static Pattern PATTERN = Pattern.compile(PATTERN_STRING);
}
