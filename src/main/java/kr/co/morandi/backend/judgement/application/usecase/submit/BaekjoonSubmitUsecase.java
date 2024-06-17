package kr.co.morandi.backend.judgement.application.usecase.submit;


import kr.co.morandi.backend.common.annotation.Usecase;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_management.domain.error.SessionErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_record.application.port.out.record.RecordPort;
import kr.co.morandi.backend.defense_record.domain.error.RecordErrorCode;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.judgement.application.port.out.BaekjoonSubmitPort;
import kr.co.morandi.backend.judgement.application.request.JudgementServiceRequest;
import kr.co.morandi.backend.judgement.application.service.SubmitFacade;
import kr.co.morandi.backend.judgement.domain.event.TempCodeSaveEvent;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Usecase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BaekjoonSubmitUsecase {

    private final BaekjoonSubmitPort baekjoonSubmitPort;
    private final DefenseSessionPort defenseSessionport;
    private final RecordPort recordPort;
    private final MemberPort memberPort;
    private final SubmitFacade submitFacade;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void judgement(final JudgementServiceRequest request) {
        final Long memberId = request.getMemberId();
        final Long defenseSessionId = request.getDefenseSessionId();
        final Language language = request.getLanguage();
        final String sourceCode = request.getSourceCode();
        final Long problemNumber = request.getProblemNumber();
        final SubmitVisibility submitVisibility = request.getSubmitVisibility();
        final LocalDateTime nowDateTime = request.getNowDateTime();

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
        * 외부 API 요청이 트랜잭션을 잡고 있는 것을 최소화하기 위함
        *
        * 채점 시작을 비동기 별도 스레드로 처리하고
        * 채점 결과를 받아서 성공하면 그 결과를 채점 기록에 저장한다.
        * */
        submitFacade.asyncProcessSubmitAndSubscribeJudgement(savedSubmit.getSubmitId(), memberId,
                detail.getProblem(), language, sourceCode, submitVisibility, nowDateTime);

        /*
         * 비동기로 시험 채점 서비스를 호출했던 코드를 TempCode에 저장한다.
         * 이 요청은 이벤트로 발행하여 TempCode 저장이 비즈니스 로직 실행에 영향을 주지 않도록 한다.
         * */
        applicationEventPublisher.publishEvent(new TempCodeSaveEvent(defenseSessionId, problemNumber, sourceCode, language));
    }
}
