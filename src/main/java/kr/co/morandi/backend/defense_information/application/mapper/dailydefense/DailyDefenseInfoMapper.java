package kr.co.morandi.backend.defense_information.application.mapper.dailydefense;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyDefenseInfoMapper {

    public static DailyDefenseInfoResponse fromNonAttempted(DailyDefense dailyDefense) {
        return DailyDefenseInfoResponse.builder()
                .defenseName(dailyDefense.getContentName())
                .problemCount(dailyDefense.getProblemCount())
                .attemptCount(dailyDefense.getAttemptCount())
                .problems(DailyDefenseProblemInfoMapper.ofNonAttempted(dailyDefense.getDailyDefenseProblems()))
                .build();
    }

    public static DailyDefenseInfoResponse ofAttempted(DailyDefense dailyDefense, DailyRecord dailyRecord) {
        return DailyDefenseInfoResponse.builder()
                .defenseName(dailyDefense.getContentName())
                .problemCount(dailyDefense.getProblemCount())
                .attemptCount(dailyDefense.getAttemptCount())
                .problems(DailyDefenseProblemInfoMapper.ofAttempted(
                        dailyDefense.getDailyDefenseProblems(),
                        dailyRecord.getSolvedProblemNumbers())
                )
                .build();
    }
}
