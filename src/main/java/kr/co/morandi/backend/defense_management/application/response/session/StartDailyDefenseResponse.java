package kr.co.morandi.backend.defense_management.application.response.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StartDailyDefenseResponse {

    private Long defenseSessionId;
    private String contentName;
    private DefenseType defenseType;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastAccessTime;
    private List<DefenseProblemResponse> defenseProblems;

    @Builder
    private StartDailyDefenseResponse(Long defenseSessionId,
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
