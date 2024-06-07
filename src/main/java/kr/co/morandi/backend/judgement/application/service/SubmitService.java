package kr.co.morandi.backend.judgement.application.service;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.application.service.baekjoon.result.JudgementResultSubscriber;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmitService {

    private final SubmitStrategy submitStrategy;
    private final JudgementResultSubscriber judgementResultSubscriber;

    /*
    * 외부 API이기 때문에 트랜잭션 내에서 수행하지 않고
    * 비동기로 처리한다.
    * */
    @Async("baekjoonJudgementExecutor")
    public void asyncProcessSubmitAndSubscribeJudgement(final Long submitId,
                                                        final Problem problem,
                                                        final Language language,
                                                        final String sourceCode,
                                                        final SubmitVisibility submitVisibility) {

        final String solutionId = submitStrategy.submit(language, problem, sourceCode, submitVisibility);
        /*
         * solutionId를 바탕으로 websocket을 채널을 등록하는 로직
         * */
        judgementResultSubscriber.subscribeJudgement(solutionId, submitId);
    }

}