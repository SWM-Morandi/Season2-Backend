package kr.co.morandi.backend.domain.exammanagement.management.response;

import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.DefenseType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartDailyDefenseServiceResponse {

    private Long defenseSessionId;
    private String contentName;
    private DefenseType defenseType;
    private LocalDateTime lastAccessTime;
    private List<DefenseProblemResponse> defenseProblems;

    public static StartDailyDefenseServiceResponse of(Long defenseSessionId,
                                                      Defense defense,
                                                      LocalDateTime lastAccessTime,
                                                      List<DefenseProblemResponse> defenseProblems) {
        return StartDailyDefenseServiceResponse.builder()
                .defenseSessionId(defenseSessionId)
                .contentName(defense.getContentName())
                .defenseType(defense.getDefenseType())
                .lastAccessTime(lastAccessTime)
                .defenseProblems(defenseProblems)
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
