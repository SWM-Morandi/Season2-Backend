package kr.co.morandi.backend.defense_management.application.service.judgement;

import kr.co.morandi.backend.defense_management.infrastructure.adapter.judgement.BaekjoonSubmitAdapter;
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
        final String languageCode = BaekjoonJudgementLanguageCode.getLanguageCode(language);
        final String cookie = baekjoonMemberCookieService.getCurrentMemberCookie();
        final String submitVisibilityCode = BaekjoonSubmitVisuability.getSubmitVisibilityCode(submitVisibility);

        final String solutionId = baekjoonSubmitAdapter.submitAndGetSolutionId(baejoonProblemId, cookie, languageCode, sourceCode, submitVisibilityCode);

        /*
        * solutionId를 바탕으로 websocket을 비동기로 구독하는 로직
        * */



        return true;
    }


}
