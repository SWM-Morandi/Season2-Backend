package kr.co.morandi.backend.defense_management.domain.model.judgement;

import kr.co.morandi.backend.defense_management.infrastructure.baekjoon.judgement.PusherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class JudgementResultService {
    private final Map<String, String> solutionResultMap = new ConcurrentHashMap<>();
    private final PusherService pusherService;
    private static final String CHANNEL_FORMAT = "solution-%s";

    private static final String INIT = "INIT";
    public void subscribeJudgement(String solutionId) {
        String solutionChannelId = String.format(CHANNEL_FORMAT, solutionId);
        solutionResultMap.put(solutionChannelId, INIT);

        pusherService.subscribeJudgement(solutionChannelId, (channelName, data) -> handleResult(solutionChannelId, data));
    }

    private void handleResult(String solutionId, String data) {
        // data 파싱

        log.info("solutionId = {} : {}", solutionId, data);
    }


    public int parseResultFromData(String data) {
        return Integer.parseInt(data.replaceAll("[^0-9]", ""));
    }

    public String parseProgressFromData(String data) {
        // JSON 파싱 로직 (간단히 가정)
        return data.contains("progress") ? data.split(":")[1].replaceAll("[^0-9]", "") : null;
    }



}
