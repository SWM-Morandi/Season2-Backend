package kr.co.morandi.backend.defense_management.application.response.session;

import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartDailyDefenseServiceResponse {

    private Long defenseSessionId;
    private String contentName;
    private DefenseType defenseType;
    private LocalDateTime lastAccessTime;
    private List<DefenseProblemResponse> defenseProblems;

    public static StartDailyDefenseServiceResponse from(Map<Long, Problem> tryProblem,
                                                        DailyDefense dailyDefense,
                                                        DefenseSession defenseSession,
                                                        DailyRecord dailyRecord) {
        return StartDailyDefenseServiceResponse.builder()
                .defenseSessionId(defenseSession.getDefenseSessionId())
                .contentName(dailyDefense.getContentName())
                .defenseType(dailyDefense.getDefenseType())
                .lastAccessTime(defenseSession.getLastAccessDateTime())
                .defenseProblems(DefenseProblemResponse.fromDailyDefense(tryProblem, defenseSession, dailyRecord))
                .build();
    }

    @Builder
    private StartDailyDefenseServiceResponse(Long defenseSessionId,
                                             String contentName,
                                             DefenseType defenseType,
                                             LocalDateTime lastAccessTime,
                                             List<DefenseProblemResponse> defenseProblems) {
        this.defenseSessionId = defenseSessionId;
        this.defenseType = defenseType;
        this.contentName = contentName;
        this.lastAccessTime = lastAccessTime;
        this.defenseProblems = defenseProblems;
    }
}
