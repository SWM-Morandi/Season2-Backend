package kr.co.morandi.backend.defense_management.application.mapper.session;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_management.application.mapper.defenseproblem.DefenseProblemMapper;
import kr.co.morandi.backend.defense_management.application.response.session.StartDailyDefenseResponse;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StartDailyDefenseMapper {

    public static StartDailyDefenseResponse of(Map<Long, Problem> tryProblem,
                                               DailyDefense dailyDefense,
                                               DefenseSession defenseSession,
                                               DailyRecord dailyRecord) {
        return StartDailyDefenseResponse.builder()
                .defenseSessionId(defenseSession.getDefenseSessionId())
                .contentName(dailyDefense.getContentName())
                .defenseType(dailyDefense.getDefenseType())
                .lastAccessTime(defenseSession.getLastAccessDateTime())
                .defenseProblems(DefenseProblemMapper.of(tryProblem, defenseSession, dailyRecord))
                .build();
    }
}
