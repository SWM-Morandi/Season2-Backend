package kr.co.morandi.backend.defense_management.application.service.judgement;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

public interface JudgementStrategy {
    boolean judge(Language language, Problem problem, String sourceCode, String submitVisibility);
}
