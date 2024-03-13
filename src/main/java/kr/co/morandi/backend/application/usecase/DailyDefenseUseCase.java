package kr.co.morandi.backend.application.usecase;

import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.port.DailyDefensePort;
import kr.co.morandi.backend.domain.defense.port.record.DailyRecordPort;
import kr.co.morandi.backend.domain.defense.service.ProblemGenerationService;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.record.dailydefense.DailyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

import static kr.co.morandi.backend.domain.defense.model.DefenseType.DAILY;

@Component
@RequiredArgsConstructor
public class DailyDefenseUseCase {

    private final DailyDefensePort dailyDefensePort;
    private final ProblemGenerationService problemGenerationService;
    private final DailyRecordPort dailyRecordPort;

    public void startDailyTest(Member member) {
        // DailyDefense를 가져온다.
        final LocalDateTime now = LocalDateTime.now();
        final DailyDefense dailyDefense = dailyDefensePort.findDailyDefense(DAILY, now.toLocalDate());

        // dailyDefense의 문제 목록을 가져온다.
        Map<Long, Problem> problems = dailyDefense.getDefenseProblems(problemGenerationService);

        // 문제 목록을 통해 테스트를 시작한다.
        final DailyRecord dailyRecord = DailyRecord.create(now, dailyDefense, member, problems);

        final DailyRecord saveDailyRecord = dailyRecordPort.saveDailyRecord(dailyRecord);



    }
}
