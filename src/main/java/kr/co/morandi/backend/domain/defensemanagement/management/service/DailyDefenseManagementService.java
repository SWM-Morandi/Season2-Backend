package kr.co.morandi.backend.domain.defensemanagement.management.service;

import kr.co.morandi.backend.application.port.out.defense.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.application.port.out.defensemanagement.defensesession.DefenseSessionPort;
import kr.co.morandi.backend.application.port.out.record.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defense.problemgenerationstrategy.service.ProblemGenerationService;
import kr.co.morandi.backend.domain.defensemanagement.management.request.StartDailyDefenseServiceRequest;
import kr.co.morandi.backend.domain.defensemanagement.management.response.StartDailyDefenseServiceResponse;
import kr.co.morandi.backend.domain.defensemanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static kr.co.morandi.backend.domain.defense.DefenseType.DAILY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyDefenseManagementService {

    private final DailyDefensePort dailyDefensePort;
    private final DailyRecordPort dailyRecordPort;
    private final ProblemGenerationService problemGenerationService;
    private final DefenseSessionPort defenseSessionPort;

    @Transactional
    public StartDailyDefenseServiceResponse startDailyDefense(StartDailyDefenseServiceRequest request, Member member, LocalDateTime requestTime) {
        Long problemNumber = request.getProblemNumber();

        // 세션이랑 세션 Detail을 찾아서 응시 기록이 있는지 살펴보기
        final Optional<DefenseSession> maybeDefenseSession = defenseSessionPort.findTodaysDailyDefenseSession(member, requestTime);

        // 오늘의 Defense를 찾아오기
        final DailyDefense dailyDefense = dailyDefensePort.findDailyDefense(DAILY, requestTime.toLocalDate());

        // 오늘의 문제 목록 중에서 원하는 문제를 찾아서 시도하려는 문제 목록에 추가 (오늘의 문제 목록에 해당 문제가 없으면 예외 발생)
        final Map<Long, Problem> tryProblem = dailyDefense.getTryingProblem(problemNumber, problemGenerationService);

        // DefenseSession이 있으면 get, 없으면 새로운 DefenseSession을 생성
        final DefenseSession defenseSession = maybeDefenseSession.orElseGet(() -> createNewSession(member, requestTime, dailyDefense, tryProblem));

        // DefenseSession의 recordId로 DailyRecord를 찾고 문제를 시도했는지 확인하고 시도하지 않았으면 시도하도록 함
        Long recordId = defenseSession.getRecordId();
        DailyRecord dailyRecord = dailyRecordPort.findDailyRecord(member, recordId, requestTime.toLocalDate())
                .orElseThrow(() -> new IllegalArgumentException("세션에 해당하는 응시 기록이 없습니다."));

        if (!defenseSession.hasTriedProblem(problemNumber)) {
            dailyRecord.tryMoreProblem(tryProblem);
            defenseSession.tryMoreProblem(problemNumber, requestTime);
        }

        final DefenseSession savedDefenseSession = defenseSessionPort.saveDefenseSession(defenseSession);

        // 문제 목록을 DefenseProblemResponse DTO로 변환
        return StartDailyDefenseServiceResponse.from(tryProblem, dailyDefense, savedDefenseSession, dailyRecord);
    }
    private DefenseSession createNewSession(Member member, LocalDateTime now, DailyDefense dailyDefense, Map<Long, Problem> tryProblem) {
        DailyRecord dailyRecord = DailyRecord.tryDefense(now, dailyDefense, member, tryProblem);
        DailyRecord savedDailyRecord = dailyRecordPort.saveDailyRecord(dailyRecord);
        Long recordId = savedDailyRecord.getRecordId();

        return DefenseSession.startSession(member, recordId, dailyDefense.getDefenseType(), tryProblem.keySet(), now, dailyDefense.getEndTime(now));
    }


}
