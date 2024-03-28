package kr.co.morandi.backend.domain.defensemanagement.management.response;

import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defensemanagement.session.model.DefenseSession;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
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
