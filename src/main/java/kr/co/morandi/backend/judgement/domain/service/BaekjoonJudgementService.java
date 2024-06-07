package kr.co.morandi.backend.judgement.domain.service;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.application.port.out.BaekjoonSubmitPort;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonJudgementResult;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementResult;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaekjoonJudgementService {

    private final BaekjoonSubmitPort baekjoonSubmitPort;

    /*
    * Pusher의 결과를 받는 스레드를 블로킹하지 않기 위해 비동기로 처리하는 메서드입니다.
    * TODO 실패 시 어떻게 해야할 지
    * Pusher가 단일 스레드가 아니라면 Async를 사용하지 않아도 괜찮습니다. (I/O 시 다른 스레드가 처리할 수 있기 때문)
    * */
    @Async("baekjoonJudgementExecutor")
    @Transactional
    public void asyncUpdateJudgementStatus(final Long submitId,
                                           final JudgementStatus judgementStatus,
                                           final Integer memory,
                                           final Integer time,
                                           final BaekjoonJudgementResult baekjoonJudgementResult) {

        log.info("Update Judgement Status submitId: {}, judgementStatus: {}, memory: {}, time: {}", submitId, judgementStatus, memory, time);

        final BaekjoonSubmit submit = baekjoonSubmitPort.findSubmit(submitId)
                .orElseThrow(() -> new MorandiException(JudgementResultErrorCode.SUBMIT_NOT_FOUND));

        JudgementResult judgementResult = getJudgementResult(judgementStatus, memory, time);

        submit.updateJudgementResult(judgementResult, baekjoonJudgementResult);

        // TODO 여기서 결정된 정답 여부를 바탕으로 Detail도 업데이트 해야하는데, 직접 의존해야할지, 이벤트를 이용해야 할지
        baekjoonSubmitPort.save(submit);
    }
    private JudgementResult getJudgementResult(JudgementStatus judgementStatus, Integer memory, Integer time) {
        if(judgementStatus.equals(JudgementStatus.ACCEPTED))
            return JudgementResult.accepted(memory, time);
        return JudgementResult.rejected(judgementStatus);
    }


}
