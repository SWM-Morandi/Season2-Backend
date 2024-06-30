package kr.co.morandi.backend.defense_management.domain.model.judgement;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.judgement.application.service.baekjoon.result.JudgementResultSubscriber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JudgementResultSubscriberTest extends IntegrationTestSupport {

    @Autowired
    private JudgementResultSubscriber judgementResultSubscriber;

    @DisplayName("")
    @Test
    void test() throws InterruptedException {
        // given

        // when
//        for(int i = 79196920;i<79197000;i++){
//            judgementResultService.subscribeJudgement(String.valueOf(i), );


        // then
//        Thread.sleep(100000L);

    }

}