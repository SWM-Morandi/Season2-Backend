package kr.co.morandi.backend.judgement.application.usecase.submit;


import kr.co.morandi.backend.common.annotation.Usecase;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.application.port.out.BaekjoonSubmitPort;
import kr.co.morandi.backend.judgement.application.service.SubmitStrategy;
import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.judgement.application.request.JudgementServiceRequest;
import kr.co.morandi.backend.defense_management.domain.error.SessionErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_record.application.port.out.record.RecordPort;
import kr.co.morandi.backend.defense_record.domain.error.RecordErrorCode;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.judgement.application.service.baekjoon.result.JudgementResultService;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Usecase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaekjoonSubmitUsecase {

    private final DefenseSessionPort defenseSessionport;
    private final RecordPort recordPort;
    private final MemberPort memberPort;
    private final SubmitStrategy submitStrategy;
    private final BaekjoonSubmitPort baekjoonSubmitPort;
    private final JudgementResultService judgementResultService;

    @Transactional
    public void judgement(final JudgementServiceRequest request) {
        final Long memberId = request.getMemberId();
        final Long defenseSessionId = request.getDefenseSessionId();
        final Language language = request.getLanguage();
        final String sourceCode = request.getSourceCode();
        final Long problemNumber = request.getProblemNumber();
        final SubmitVisibility submitVisibility = request.getSubmitVisibility();

        DefenseSession defenseSession = defenseSessionport.findDefenseSessionById(defenseSessionId)
                .orElseThrow(() -> new MorandiException(SessionErrorCode.SESSION_NOT_FOUND));

        Member mebmer = memberPort.findById(memberId)
                .orElseThrow(() -> new MorandiException(OAuthErrorCode.MEMBER_NOT_FOUND));

        defenseSession.validateSessionOwner(memberId);

        /*
        * 시험이 종료되어 있는지 확인한다.
        * */
        if (defenseSession.isTerminated()) {
            throw new MorandiException(SessionErrorCode.SESSION_ALREADY_ENDED);
        }

        /*
        * DefenseSession에 대응하는
        * Record를 찾아온다. Fetch Join을 통해 Detail, Problem을 같이 가져온다.
        * */
        final Record<? extends Detail> defenseRecord = recordPort.findRecordFetchJoinWithDetailAndProblem(defenseSession.getRecordId())
                .orElseThrow(() -> new MorandiException(RecordErrorCode.RECORD_NOT_FOUND));

        /*
        * Record가 종료되어 있는지 확인한다.
        * */
        if (defenseRecord.isTerminated()) {
            throw new MorandiException(RecordErrorCode.RECORD_ALREADY_TERMINATED);
        }
        /*
        * 문제 번호로 Detail을 찾아온다.
        * */
        final Detail detail = defenseRecord.getDetail(problemNumber);
        /*
        * 제출 기록을 저장한다.
        * */
        final BaekjoonSubmit submit = BaekjoonSubmit.submit(mebmer, detail, SubmitCode.of(sourceCode, language), submitVisibility, 0);
        final BaekjoonSubmit savedSubmit = baekjoonSubmitPort.save(submit);

        /*
        * 채점 시작을 비동기 별도 스레드로 처리하고
        * 채점 결과를 받아서 성공하면 그 결과를 채점 기록에 저장한다.
        * */
        Problem problem = detail.getProblem();
        final String solutionId = submitStrategy.submit(language, problem, sourceCode, submitVisibility);

        /*
         * solutionId를 바탕으로 websocket을 비동기로 구독하는 로직
         * */
        judgementResultService.subscribeJudgement(solutionId, savedSubmit.getSubmitId());

        /*
         * 비동기로 시험 채점 서비스를 호출했던 코드를 TempCode에 저장한다.
         * 이 때, 주의할 점
         * */

        return;
    }
}
