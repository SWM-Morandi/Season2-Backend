package kr.co.morandi.backend.judgement.application.usecase.baekjoon;


import kr.co.morandi.backend.common.exception.MorandiException;
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
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JudgementUsecase {

    private final DefenseSessionPort defenseSessionport;
    private final RecordPort recordPort;
    private final MemberPort memberPort;
    private final SubmitStrategy judgementPort;

    @Transactional
    public void judgement(final JudgementServiceRequest request) {
        final Long memberId = request.getMemberId();
        final Long defenseSessionId = request.getDefenseSessionId();
        final Language language = request.getLanguage();
        final String sourceCode = request.getSourceCode();
        final Long problemNumber = request.getProblemNumber();
        final String submitVisibility = request.getSubmitVisibility();

        DefenseSession defenseSession = defenseSessionport.findDefenseSessionById(defenseSessionId)
                .orElseThrow(() -> new MorandiException(SessionErrorCode.SESSION_NOT_FOUND));

//        Member mebmer = memberPort.findById(memberId)
//                .orElseThrow(() -> new MorandiException(OAuthErrorCode.MEMBER_NOT_FOUND));

        defenseSession.validateSessionOwner(memberId);

        /*
        * 시험이 종료되어 있는지 확인한다.
        * */
        if (defenseSession.isTerminated()) {
            throw new MorandiException(SessionErrorCode.SESSION_ALREADY_ENDED);
        }

        /*
        * DefenseSession에 대응하는
        * Record를 찾아온다.
        * */
        final Record<? extends Detail> defenseRecord = recordPort.findRecordById(defenseSession.getRecordId())
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


        Problem problem = detail.getProblem();

        /*
        * 비동기로 시험 채점 서비스를 호출했던 코드를 TempCode에 저장한다.
        * 이 때, 주의할 점
        * */


        /*
        * 채점 결과를 받아서 성공하면 그 결과를 채점 기록에 저장한다.
        * */


        /*
        * 채점 시작을 비동기 별도 스레드로 처리하고
        * 채점 결과를 받아서 성공하면 그 결과를 채점 기록에 저장한다.
        * */
        judgementPort.submit(language, problem, sourceCode, submitVisibility);


    }
}
