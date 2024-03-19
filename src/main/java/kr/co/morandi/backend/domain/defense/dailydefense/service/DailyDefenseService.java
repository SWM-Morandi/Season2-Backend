package kr.co.morandi.backend.domain.defense.dailydefense.service;

import kr.co.morandi.backend.application.port.out.defense.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.application.port.out.record.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defense.problemGenerationStrategy.service.ProblemGenerationService;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defense.DefenseType.DAILY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyDefenseService {

    private final DailyDefensePort dailyDefensePort;
    private final DailyRecordPort dailyRecordPort;
    private final ProblemGenerationService problemGenerationService;

    /*
    *  원하는 문제로 DailyDefense를 시작
    * */
    @Transactional
    public DailyRecord startDailyDefense(LocalDateTime now, Member member, Long problemNumber) {
        // 오늘의 Defense를 찾아옴
        final DailyDefense dailyDefense = dailyDefensePort.findDailyDefense(DAILY, now.toLocalDate());

        // 오늘의 문제 목록 중에서 원하는 문제를 찾아서 문제 목록에 추가
        Map<Long, Problem> tryProblem = dailyDefense.getDefenseProblems(problemGenerationService).entrySet().stream()
                .filter(p -> p.getKey().equals(problemNumber))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 오늘의 문제 목록에 해당 문제가 없으면 예외 발생
        if (tryProblem.isEmpty()) {
            throw new IllegalArgumentException("해당 문제가 오늘의 문제 목록에 없습니다.");
        }

        // 오늘의 문제 응시 기록을 찾아옵
        Optional<DailyRecord> maybeDailyRecord = dailyRecordPort.findDailyRecord(member, now.toLocalDate());

        // 만약 오늘의 DefenseRecord 기록이 있고, 문제 기록이 없으면 목록에 문제 추가
        if(maybeDailyRecord.isPresent()) {
            final DailyRecord dailyRecord = maybeDailyRecord.get();
            dailyRecord.tryMoreProblem(tryProblem);
            dailyRecordPort.saveDailyRecord(dailyRecord);

            // Map에서 tryProblem 목록을 dailyRecord의 PK와 함께 반환
            return dailyRecord;
        }

        // 기존 오늘의 DefenseRecord 기록이 없다면 새로 생성
        final DailyRecord dailyRecord = dailyRecordPort.saveDailyRecord(DailyRecord.tryDefense(now, dailyDefense, member, tryProblem));

        // Map에서 tryProblem 목록을 dailyRecord의 PK와 함께 반환 구현할 것
        return dailyRecord;
    }
}
