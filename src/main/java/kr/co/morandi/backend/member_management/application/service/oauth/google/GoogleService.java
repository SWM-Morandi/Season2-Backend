package kr.co.morandi.backend.member_management.application.service.oauth.google;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants.OAuthUserInfo;
import kr.co.morandi.backend.member_management.domain.model.member.SocialType;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.response.TokenResponse;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants.google.GoogleOAuthUserInfo;
import kr.co.morandi.backend.member_management.application.service.oauth.OAuthService;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleService implements OAuthService {

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.google.redirect-callback-url}")
    private String googleClientRedirectUrl;

    @Value("${oauth2.google.api-token-url}")
    private String googleApiTokenUrl;

    @Value("${oauth2.google.userinfo-url}")
    private String googleUserInfoUrl;

    @Value("${oauth2.google.type}")
    private String type;

    private final WebClient webClient;
    @Override
    public String getType() {
        return type;
    }
    @Override
    public String getAccessToken(String authorizationCode) {
        LinkedMultiValueMap<String, String> params = buildParams(authorizationCode);
        TokenResponse tokenResponse = getTokenResponse(params);

        return tokenResponse.getAccess_token();
    }
    private LinkedMultiValueMap<String, String> buildParams(String authorizationCode) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", googleClientRedirectUrl);
        return params;
    }
    private TokenResponse getTokenResponse(LinkedMultiValueMap<String, String> params) {
        TokenResponse tokenResponse =  webClient.post()
                .uri(googleApiTokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromValue(params))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .retry(3)
                .block();

        if(tokenResponse == null) {
            throw new MorandiException(OAuthErrorCode.GOOGLE_OAUTH_ERROR);
        }

        return tokenResponse;
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = getBearerHeader(accessToken);
        return getGoogleUserDto(headers);
    }
    private HttpHeaders getBearerHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
    private GoogleOAuthUserInfo getGoogleUserDto(HttpHeaders headers) {
        GoogleOAuthUserInfo googleUserDto = webClient.get()
                .uri(googleUserInfoUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(GoogleOAuthUserInfo.class)
                .block();
        googleUserDto.setSocialType(SocialType.GOOGLE);
        return googleUserDto;
    }
}
