package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonJudgementResult;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementStatus;
import kr.co.morandi.backend.judgement.domain.service.BaekjoonJudgementService;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.result.PusherService;
import kr.co.morandi.backend.judgement.infrastructure.helper.JudgementStatusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JudgementResultSubscriber {

    private final PusherService pusherService;
    private final ObjectMapper objectMapper;
    private final BaekjoonJudgementService baekjoonJudgementService;
    private static final String CHANNEL_FORMAT = "solution-%s";

    public void subscribeJudgement(final String solutionId, final Long submitId) {
        /*
        * solutionId를 바탕으로 pusher 채널을 구독하는 로직
        * */
        final String solutionChannelId = String.format(CHANNEL_FORMAT, solutionId);

        /*
        * 콜백 함수를 파라미터로 전달하여 Listener에서 메세지가 도착하면 콜백함수를 실행하도록 구현
        * */
        pusherService.subscribeJudgement(solutionChannelId, submitId, this::handleResult);
    }


    /*
    * 등록된 콜백함수에서 메세지가 도착하면 실행되는 함수
    *
    * 결과를 파싱하여 저장하는 로직
    * */
    private void handleResult(final Long submitId, final String data) {
        final BaekjoonJudgementStatus baekjoonJudgementStatus = parseJudgementStatus(data);

        if(baekjoonJudgementStatus.isFinalResult()) {
            pusherService.unsubscribeJudgement(String.format(CHANNEL_FORMAT, submitId));

            log.info("BaekjoonJudgement : submitId: {}, status: {}", submitId, baekjoonJudgementStatus);

            final JudgementStatus judgementStatus = JudgementStatusMapper.mapToJudgementStatus(baekjoonJudgementStatus.getResult());

            final Integer memory = baekjoonJudgementStatus.getMemory();
            final Integer time = baekjoonJudgementStatus.getTime();

            // JudgementStatus를 바탕으로 DB에 저장하는 로직
            // 비동기로 처리해야 PusherService의 스레드가 블로킹되지 않음
            baekjoonJudgementService.asyncUpdateJudgementStatus(submitId, judgementStatus, memory, time, BaekjoonJudgementResult.defaultResult());
        }
    }

    private BaekjoonJudgementStatus parseJudgementStatus(String data) {
        try {
            final BaekjoonJudgementStatus baekjoonJudgementStatus = objectMapper.readValue(data, BaekjoonJudgementStatus.class);
            if(baekjoonJudgementStatus != null) {
                return baekjoonJudgementStatus;
            }
        } catch (JsonProcessingException e) {
            throw new MorandiException(JudgementResultErrorCode.INVALID_JUDGEMENT_RESULT);
        }
        throw new MorandiException(JudgementResultErrorCode.INVALID_JUDGEMENT_RESULT);
    }

}
