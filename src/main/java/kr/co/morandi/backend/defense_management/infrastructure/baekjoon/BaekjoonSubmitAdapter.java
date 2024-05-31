package kr.co.morandi.backend.defense_management.infrastructure.baekjoon;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.domain.model.judgement.JudgementErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

import static kr.co.morandi.backend.defense_management.infrastructure.baekjoon.BaekjoonJudgementConstants.BAEKJOON_SUBMIT_URL;
import static kr.co.morandi.backend.defense_management.infrastructure.baekjoon.BaekjoonJudgementConstants.BAEKJOON_USER_AGENT;
import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
@RequiredArgsConstructor
public class BaekjoonSubmitAdapter {

    private final WebClient webClient;
    private final BaekjoonHtmlParser baekjoonHtmlParser;

    /*
    * 제출을 하고 솔루션 아이디를 가져오는 메소드
    */
    public String submitAndGetSolutionId(String baekjoonProblemId, String cookie, Language language, String sourceCode, String submitVisibility) {
        String csrfKey = getCsrfKeyFromSubmitPage(cookie, baekjoonProblemId);

        final String languageCode = BaekjoonJudgementLanguageCode.getLanguageCode(language);
        final String submitVisibilityCode = BaekjoonSubmitVisuability.getSubmitVisibilityCode(submitVisibility);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("problem_id", baekjoonProblemId);
        parameters.add("language", languageCode);
        parameters.add("source", sourceCode);
        parameters.add("code_open", submitVisibilityCode);
        parameters.add("csrf_key", csrfKey);


        final String resultHtml = webClient.post()
                .uri(String.format(BAEKJOON_SUBMIT_URL, baekjoonProblemId))
                .header(USER_AGENT, BAEKJOON_USER_AGENT)
                .header(COOKIE, "OnlineJudge=" + cookie)
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(parameters))
                .exchangeToMono(response -> handleRedirection(response, baekjoonProblemId))
                .block();

        return baekjoonHtmlParser.parseSolutionIdFromHtml(resultHtml);
    }

    /*
     * 제출에 필요한 csrf_key를 가져오는 메소드
     * */
    private String getCsrfKeyFromSubmitPage(String cookie, String baekjoonProblemId) {
        final String submitPageHtml = webClient.get()
                .uri(String.format(BAEKJOON_SUBMIT_URL, baekjoonProblemId))
                .header(USER_AGENT, BAEKJOON_USER_AGENT)
                .header(HttpHeaders.COOKIE, "OnlineJudge=" + cookie)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return baekjoonHtmlParser.parseCsrfKeyInSubmitPage(submitPageHtml);
    }


    private Mono<String> handleRedirection(ClientResponse initialResponse, String baekjoonProblemId) {
        /*
        * 제출 후 일반적으로 3xx redirection이 발생합니다.
        * 3xx redirection이 발생하면 location을 가져와서 다시 요청합니다.
        * */
        if (initialResponse.statusCode().is3xxRedirection()) {
            URI locationUri = initialResponse.headers().asHttpHeaders().getLocation();

            if (locationUri == null) {
                throw new MorandiException(JudgementErrorCode.REDIRECTION_LOCATION_NOT_FOUND);
            }

            String location = locationUri.toString();

            if (location == null || location.isEmpty()) {
                throw new MorandiException(JudgementErrorCode.REDIRECTION_LOCATION_NOT_FOUND);
            }

            if (!location.startsWith("http")) {
                location = URI.create(String.format(BAEKJOON_SUBMIT_URL, baekjoonProblemId))
                        .resolve(location)
                        .toString();
            }

            /*
            * 직접 Redirect할 경우에는 사용자 쿠키가 필요 없습니다.
            * */
            return webClient.get()
                    .uri(location)
                    .header(USER_AGENT, BAEKJOON_USER_AGENT)
                    .retrieve()
                    .bodyToMono(String.class);
        }

        return initialResponse
                    .bodyToMono(String.class);
    }


}