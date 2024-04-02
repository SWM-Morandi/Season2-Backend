package kr.co.morandi.backend.defense_management.application.mapper.defenseproblem;

import kr.co.morandi.backend.defense_management.application.mapper.tempcode.TempCodeMapper;
import kr.co.morandi.backend.defense_management.application.response.session.DefenseProblemResponse;
import kr.co.morandi.backend.defense_management.application.response.tempcode.TempCodeResponse;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefenseProblemMapper {

    public static List<DefenseProblemResponse> of(Map<Long, Problem> tryProblem, DefenseSession defenseSession, DailyRecord dailyRecord) {
        return tryProblem.entrySet().stream()
                .map(entry -> {
                    final Long problemNumber = entry.getKey();
                    final Problem problem = entry.getValue();
                    final boolean isCorrect = dailyRecord.isSolvedProblem(problemNumber);

                    final SessionDetail sessionDetail = defenseSession.getSessionDetail(problemNumber);

                    final Language lastAccessLanguage = sessionDetail.getLastAccessLanguage();
                    final Set<TempCodeResponse> tempCodeResponses = TempCodeMapper.createTempCodeResponses(sessionDetail.getTempCodes());

                    return DefenseProblemResponse.builder()
                            .problemId(problem.getProblemId())
                            .baekjoonProblemId(problem.getBaekjoonProblemId())
                            .problemNumber(problemNumber)
                            .isCorrect(isCorrect)
                            .lastAccessLanguage(lastAccessLanguage)
                            .tempCodes(tempCodeResponses)
                            .build();
                })
                .toList();
    }
}
