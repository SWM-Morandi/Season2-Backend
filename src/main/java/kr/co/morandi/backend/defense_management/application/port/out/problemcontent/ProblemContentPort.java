package kr.co.morandi.backend.defense_management.application.port.out.problemcontent;

import kr.co.morandi.backend.defense_management.application.response.problemcontent.ProblemContent;

import java.util.List;
import java.util.Map;

public interface ProblemContentPort {

    Map<Long, ProblemContent> getProblemContents(List<Long> baekjoonProblemIds);
}
