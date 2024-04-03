package kr.co.morandi.backend.member_management.domain.service.oauth.google;

import kr.co.morandi.backend.member_management.domain.model.oauth.SocialType;
import kr.co.morandi.backend.member_management.domain.model.oauth.TokenResponseDto;
import kr.co.morandi.backend.member_management.domain.model.oauth.UserDto;
import kr.co.morandi.backend.member_management.domain.model.oauth.google.GoogleUserDto;
import kr.co.morandi.backend.member_management.domain.service.oauth.OAuthService;
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
        LinkedMultiValueMap<String, String> params = getParams(authorizationCode);
        TokenResponseDto tokenResponseDto = getTokenResponseDto(params);
        String accessToken = tokenResponseDto.getAccess_token();
        return accessToken;
    }
    private LinkedMultiValueMap<String, String> getParams(String authorizationCode) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", googleClientRedirectUrl);
        return params;
    }
    private TokenResponseDto getTokenResponseDto(LinkedMultiValueMap<String, String> params) {
        TokenResponseDto tokenResponseDto = webClient.post()
                .uri(googleApiTokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromValue(params))
                .retrieve()
                .bodyToMono(TokenResponseDto.class)
                .block();
        return tokenResponseDto;
    }

    @Override
    public UserDto getUserInfo(String accessToken) {
        HttpHeaders headers = getBearerHeader(accessToken);
        GoogleUserDto googleUserDto = getGoogleUserDto(headers);
        return googleUserDto;
    }
    private HttpHeaders getBearerHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }
    private GoogleUserDto getGoogleUserDto(HttpHeaders headers) {
        GoogleUserDto googleUserDto = webClient.get()
                .uri(googleUserInfoUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(GoogleUserDto.class)
                .block();
        googleUserDto.setSocialType(SocialType.GOOGLE);
        return googleUserDto;
    }
}
