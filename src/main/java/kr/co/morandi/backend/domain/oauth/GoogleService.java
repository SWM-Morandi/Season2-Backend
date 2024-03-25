package kr.co.morandi.backend.domain.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.domain.member.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static kr.co.morandi.backend.domain.oauth.OAuthConstants.GOOGLE_USERINFO_REQUEST_URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleService implements OAuthService {

    private final ObjectMapper objectMapper;

    private final WebClient webClient;

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String googleClientRedirectUri;
    @Override
    public String getType() {
        return "google";
    }
    @Override
    public String getAccessToken(String authorizationCode) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", googleClientRedirectUri);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        Mono<ResponseEntity<String>> responseEntityMono = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .body(BodyInserters.fromValue(params))
                .retrieve()
                .toEntity(String.class);

        ResponseEntity<String> responseEntity = responseEntityMono.block();

        String accessToken = objectMapper.readValue(responseEntity.getBody(), TokenResponseDto.class).getAccess_token();

        return accessToken;
    }
    @Override
    public UserDto getUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        Mono<ResponseEntity<String>> responseEntityMono = webClient.get()
                .uri(GOOGLE_USERINFO_REQUEST_URL)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .toEntity(String.class);

        ResponseEntity<String> responseEntity = responseEntityMono.block();

        GoogleUserDto googleUserDto = objectMapper.readValue(responseEntity.getBody(), GoogleUserDto.class);

        googleUserDto.setType(SocialType.GOOGLE);

        return googleUserDto;
    }
}
