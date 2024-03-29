package kr.co.morandi.backend.defense_management.application.response.session;

import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefenseProblemResponse {

    private Long problemId;
    private Long problemNumber;
    private Long baekjoonProblemId;
    private boolean isCorrect;
    private Language tempCodeLanguage;
    private String tempCode;


    public static List<DefenseProblemResponse> fromDailyDefense(Map<Long, Problem> tryProblem, DefenseSession defenseSession, DailyRecord dailyRecord) {
        return tryProblem.entrySet().stream()
                .map(entry -> {
                    final Long problemNumber = entry.getKey();
                    final Problem problem = entry.getValue();
                    final boolean isCorrect = dailyRecord.isSolvedProblem(problemNumber);

                    final SessionDetail sessionDetail = defenseSession.getSessionDetail(problemNumber);

                    final Language lastAccessLanguage = sessionDetail.getLastAccessLanguage();
                    final TempCode tempCode = sessionDetail.getTempCode(lastAccessLanguage);

                    return DefenseProblemResponse.builder()
                            .problemId(problem.getProblemId())
                            .baekjoonProblemId(problem.getBaekjoonProblemId())
                            .problemNumber(problemNumber)
                            .isCorrect(isCorrect)
                            .tempCode(tempCode.getCode())
                            .tempCodeLanguage(lastAccessLanguage)
                            .build();

                })
                .toList();
    }
    @Builder
    private DefenseProblemResponse(Long problemId, Long problemNumber, Long baekjoonProblemId, boolean isCorrect,
                                  Language tempCodeLanguage, String tempCode) {
        this.problemId = problemId;
        this.problemNumber = problemNumber;
        this.baekjoonProblemId = baekjoonProblemId;
        this.isCorrect = isCorrect;
        this.tempCodeLanguage = tempCodeLanguage;
        this.tempCode = tempCode;
    }
}
