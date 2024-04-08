package kr.co.morandi.backend.defense_management.infrastructure.adapter.problemcontent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.defense_management.application.port.out.problemcontent.ProblemContentPort;
import kr.co.morandi.backend.defense_management.application.response.problemcontent.ProblemContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProblemContentAdapter implements ProblemContentPort {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final String PROBLEM_CONTENTS_API_URL = "https://n1bcmtru2j.execute-api.ap-northeast-2.amazonaws.com/default/getBaekjoonProblemContents?baekjoonProblemIds=%s";

    @Override
    public Map<Long, ProblemContent> getProblemContents(List<Long> baekjoonProblemIds) {

        if(baekjoonProblemIds.isEmpty()) {
            return Map.of();
        }

        if(baekjoonProblemIds.size() > 10) {
            throw new IllegalArgumentException("문제 번호는 10개 이하로 요청해주세요.");
        }

        String baekjoonProblemIdsParam = baekjoonProblemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String responseBody = webClient.get()
                .uri(String.format(PROBLEM_CONTENTS_API_URL, baekjoonProblemIdsParam))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseResponse(responseBody);
    }


    private Map<Long, ProblemContent> parseResponse(String responseBody) {
        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            List<ProblemContent> problemContents = objectMapper.readValue(responseBody, new TypeReference<>() {
            });

            return problemContents.stream()
                    .filter(content -> content.getError() == null && content.getBaekjoonProblemId() != null)
                    .collect(Collectors.toMap(ProblemContent::getBaekjoonProblemId, content -> content));

        } catch (Exception e) {
            throw new RuntimeException("Error parsing problem contents", e);
        }
    }

}
