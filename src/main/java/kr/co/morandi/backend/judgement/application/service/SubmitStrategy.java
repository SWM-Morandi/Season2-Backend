package kr.co.morandi.backend.judgement.application.service;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

public interface SubmitStrategy {
    String submit(Language language, Problem problem, String sourceCode, SubmitVisibility submitVisibility);
}
