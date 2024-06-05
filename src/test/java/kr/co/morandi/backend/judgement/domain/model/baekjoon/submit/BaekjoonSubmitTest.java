package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.factory.TestDefenseFactory;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.factory.TestProblemFactory;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonJudgementResult;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Map;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static kr.co.morandi.backend.judgement.domain.model.submit.JudgementStatus.ACCEPTED;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class BaekjoonSubmitTest {

    @DisplayName("백준 제출을 생성할 수 있다.")
    @Test
    void create() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SubmitCode 제출할_코드 = SubmitCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        // when
        BaekjoonSubmit 백준_제출 = BaekjoonSubmit.builder()
                .submitCode(제출할_코드)
                .member(사용자)
                .detail(dailyRecord.getDetail(1L))
                .submitVisibility(SubmitVisibility.OPEN)
                .trialNumber(1)
                .build();

        // then
        assertThat(백준_제출)
                .isNotNull()
                .extracting("submitCode.sourceCode", "submitCode.language", "member", "detail", "submitVisibility", "trialNumber")
                .contains(제출할_코드.getSourceCode(), 제출할_코드.getLanguage(), 사용자, dailyRecord.getDetail(1L), SubmitVisibility.OPEN, 1);
    }

    @DisplayName("백준 제출을 default와 함께 정답 처리할 수 있다.")
    @Test
    void updateStatusToAccepted() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SubmitCode 제출할_코드 = SubmitCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        BaekjoonSubmit 백준_제출 = BaekjoonSubmit.builder()
                .submitCode(제출할_코드)
                .member(사용자)
                .detail(dailyRecord.getDetail(1L))
                .submitVisibility(SubmitVisibility.OPEN)
                .trialNumber(1)
                .build();


        // when
        final BaekjoonJudgementResult baekjoonJudgementResult = BaekjoonJudgementResult.defaultResult();

        백준_제출.updateStatusToAccepted(300, 30, baekjoonJudgementResult);

        // then
        assertThat(백준_제출)
                .extracting("judgementResult.judgementStatus", "judgementResult.memory", "judgementResult.time", "trialNumber", "baekjoonJudgementResult")
                .contains(ACCEPTED, 300, 30, 1, baekjoonJudgementResult);

    }

}