package kr.co.morandi.backend.defense_management.application.service.judgement.baekjoon.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.defense_management.infrastructure.baekjoon.judgement.PusherService;
import kr.co.morandi.backend.defense_management.application.service.judgement.baekjoon.result.JudgementResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class JudgementResultService {
    private final Map<String, JudgementResult> judgementResultMap = new ConcurrentHashMap<>();
    private final PusherService pusherService;
    private final ObjectMapper objectMapper;
    private static final String CHANNEL_FORMAT = "solution-%s";

    public void subscribeJudgement(final String solutionId) {
        final String solutionChannelId = String.format(CHANNEL_FORMAT, solutionId);

        pusherService.subscribeJudgement(solutionChannelId, (channelName, data) -> handleResult(solutionChannelId, data));
    }

    private void handleResult(final String solutionId, final String data) {

        final JudgementResult judgementResult = parseJudgementResult(data);

        judgementResultMap.put(solutionId, judgementResult);

        if(judgementResult == null) {
            log.error("Failed to parse judgement result data : {}", data);
            return;
        }


    }

    private JudgementResult parseJudgementResult(String data) {
        try {
            return objectMapper.readValue(data, JudgementResult.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse judgement result data : {}", data);
            return null;
        }
    }



}
