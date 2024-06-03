package kr.co.morandi.backend.judgement.application.service.baekjoon.submit;

import kr.co.morandi.backend.judgement.application.service.JudgementStrategy;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit.BaekjoonSubmitAdapter;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaekjoonJudgementStrategy implements JudgementStrategy {

    private final BaekjoonSubmitAdapter baekjoonSubmitAdapter;
    private final BaekjoonMemberCookieService baekjoonMemberCookieService;

    @Override
    public boolean judge(Language language, Problem problem, String sourceCode, String submitVisibility) {
        final String baejoonProblemId = String.valueOf(problem.getBaekjoonProblemId());
        final String cookie = baekjoonMemberCookieService.getCurrentMemberCookie();

        final String solutionId = baekjoonSubmitAdapter.submitAndGetSolutionId(baejoonProblemId, cookie, language, sourceCode, submitVisibility);

        /*
        * solutionId를 바탕으로 websocket을 비동기로 구독하는 로직
        * */



        return true;
    }


}
