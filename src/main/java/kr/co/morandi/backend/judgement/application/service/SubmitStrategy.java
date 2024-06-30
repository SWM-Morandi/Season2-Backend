package kr.co.morandi.backend.judgement.application.service;

import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;

import java.time.LocalDateTime;

public interface SubmitStrategy {
    String submit(Long memberId, Language language, Problem problem, String sourceCode, SubmitVisibility submitVisibility, LocalDateTime nowDateTime);
}
