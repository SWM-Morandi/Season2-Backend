package kr.co.morandi.backend.member_management.domain.service.oauth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.AuthErrorCode;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleService implements OAuthService {

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.google.redirect-url}")
    private String googleClientRedirectUrl;

    @Value("${oauth2.google.api-token-url}")
    private String googleApiTokenUrl;

    @Value("${oauth2.google.userinfo-url}")
    private String googleUserInfoUrl;

    @Value("${oauth2.google.type}")
    private String type;

    private final WebClient webClient;

    private final ObjectMapper objectMapper;
    @Override
    public String getType() {
        return type;
    }
    @Override
    public String getAccessToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", googleClientRedirectUrl);

        Mono<ResponseEntity<String>> responseEntityMono = webClient.post()
                .uri(googleApiTokenUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(params))
                .retrieve()
                .toEntity(String.class);

        ResponseEntity<String> responseEntity = responseEntityMono.block();
        try {
            String accessToken = objectMapper.readValue(responseEntity.getBody(), TokenResponseDto.class).getAccess_token();
            return accessToken;
        } catch (Exception e) {
            throw new MorandiException(AuthErrorCode.TOKEN_NOT_FOUND);
        }
    }

    @Override
    public UserDto getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        Mono<ResponseEntity<String>> responseEntityMono = webClient.get()
                .uri(googleUserInfoUrl)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .toEntity(String.class);

        ResponseEntity<String> responseEntity = responseEntityMono.block();

        try {
            GoogleUserDto googleUserDto = objectMapper.readValue(responseEntity.getBody(), GoogleUserDto.class);
            googleUserDto.setType(SocialType.GOOGLE);
            return googleUserDto;
        } catch (Exception e) {
            throw new MorandiException(AuthErrorCode.SSO_USERINFO);
        }
    }
}
