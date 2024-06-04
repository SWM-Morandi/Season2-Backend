package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.result.PusherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class JudgementResultService {
    private final Map<String, JudgementResponse> judgementResultMap = new ConcurrentHashMap<>();
    private final PusherService pusherService;
    private final ObjectMapper objectMapper;
    private static final String CHANNEL_FORMAT = "solution-%s";

    public void subscribeJudgement(final String solutionId) {
        /*
        * solutionId를 바탕으로 pusher 채널을 구독하는 로직
        * */
        final String solutionChannelId = String.format(CHANNEL_FORMAT, solutionId);

        /*
        * 콜백 함수를 파라미터로 전달하여 Listener에서 메세지가 도착하면 콜백함수를 실행하도록 구현
        * */
        pusherService.subscribeJudgement(solutionChannelId, this::handleResult);
    }

    /*
    * 등록된 콜백함수에서 메세지가 도착하면 실행되는 함수
    *
    * solutionId를 통해 결과를 저장하고, 결과를 파싱하여 저장하는 로직
    * */
    private void handleResult(final String solutionId, final String data) {

        final JudgementResponse judgementResponse = parseJudgementResult(data);

        judgementResultMap.put(solutionId, judgementResponse);

        if(judgementResponse == null) {
            log.error("Failed to parse judgement result data : {}", data);
            return;
        }


    }

    private JudgementResponse parseJudgementResult(String data) {
        try {
            return objectMapper.readValue(data, JudgementResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse judgement result data : {}", data);
            return null;
        }
    }



}
