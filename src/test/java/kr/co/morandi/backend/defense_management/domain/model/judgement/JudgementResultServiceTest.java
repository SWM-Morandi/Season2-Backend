package kr.co.morandi.backend.defense_management.domain.model.judgement;

import kr.co.morandi.backend.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JudgementResultServiceTest extends IntegrationTestSupport {

    @Autowired
    private JudgementResultService judgementResultService;

    @DisplayName("")
    @Test
    void test() throws InterruptedException {
        // given
        String solutionId = "79192042";

        // when
        judgementResultService.subscribeJudgement(solutionId);

        // then
//        Thread.sleep(100000L);

    }

}