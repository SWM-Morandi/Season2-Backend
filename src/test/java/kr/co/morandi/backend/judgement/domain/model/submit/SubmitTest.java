package kr.co.morandi.backend.judgement.domain.model.submit;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.factory.TestDefenseFactory;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.factory.TestProblemFactory;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Map;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static kr.co.morandi.backend.judgement.domain.model.submit.JudgementStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class SubmitTest {

    /*
    *  추상 클래스에 관한 공통 메서드 테스트 작성을 위해
    *  Submit 클래스를 상속받는 SubmitImpl 클래스를 생성했습니다.
    */
    static class SubmitTestImpl extends Submit {
        public SubmitTestImpl(Member member, Detail detail, SourceCode sourceCode, LocalDateTime submitDateTime, SubmitVisibility submitVisibility) {
            super(member, detail, sourceCode, submitDateTime, submitVisibility);
        }
    }

    @DisplayName("submit을 여러 번 진행하면 detail의 submitCount가 1씩 증가한다.")
    @Test
    void 여러_번_제출시_Detail의_제출횟수가_증가한다() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        // when & then
        Submit 제출 = new SubmitTestImpl(사용자, dailyRecord.getDetail(1L), 제출할_코드, 제출_시간, SubmitVisibility.OPEN);
        assertThat(제출.getDetail().getSubmitCount()).isEqualTo(1L);

        new SubmitTestImpl(사용자, dailyRecord.getDetail(1L), 제출할_코드, 제출_시간, SubmitVisibility.OPEN);
        assertThat(제출.getDetail().getSubmitCount()).isEqualTo(2L);

        new SubmitTestImpl(사용자, dailyRecord.getDetail(1L), 제출할_코드, 제출_시간, SubmitVisibility.OPEN);
        assertThat(제출.getDetail().getSubmitCount()).isEqualTo(3L);

    }

    @DisplayName("submit을 진행하면 detail의 submitCount가 1 증가한다.")
    @Test
    void 제출시_Detail의_제출횟수가_증가한다() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);
        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        // when
        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        // then
        assertThat(제출)
                .extracting("detail.submitCount")
                .isEqualTo(1L);

    }

    @DisplayName("제출 시간이 null일 때 Submit을 생성하려고 하면 예외가 발생한다.")
    @Test
    void submitDateTimeIsNull() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = null;
        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        // when & then
        assertThatThrownBy(
                () -> new SubmitTestImpl(사용자,
                        dailyRecord.getDetail(1L),
                        제출할_코드,
                        제출_시간,
                        SubmitVisibility.OPEN)
        ).isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.SUBMIT_DATE_TIME_IS_NULL.getMessage());

    }

    @DisplayName("Submit 후 정답 상태로 변경할 수 있다.")
    @Test
    void updateStatusToAccepted() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);


        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(ACCEPTED)
                .memory(300)
                .time(30)
                .build();
        // when
        제출.updateJudgementResult(judgementResult);

        // then
        assertThat(제출)
                .extracting("judgementResult.judgementStatus", "judgementResult.memory", "judgementResult.time")
                .contains(ACCEPTED, 300, 30);

    }

    @DisplayName("이미 정답상태인 Submit은 다시 정답상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyAccepted() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);


        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(ACCEPTED)
                .memory(300)
                .time(30)
                .build();

        제출.updateJudgementResult(judgementResult);

        // when & then
        assertThatThrownBy(() -> 제출.updateJudgementResult(judgementResult))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_JUDGED.getMessage());

    }

    @DisplayName("이미 런타임 에러인 Submit은 다시 다른 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyRuntimeError() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(RUNTIME_ERROR)
                .memory(0)
                .time(0)
                .build();

        제출.updateJudgementResult(judgementResult);

        // when & then
        assertThatThrownBy(() -> 제출.updateJudgementResult(judgementResult))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_JUDGED.getMessage());

    }

    @DisplayName("이미 컴파일 에러인 Submit은 다시 다른 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyCompileError() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(COMPILE_ERROR)
                .memory(0)
                .time(0)
                .build();

        제출.updateJudgementResult(judgementResult);

        // when & then
        assertThatThrownBy(() -> 제출.updateJudgementResult(judgementResult))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_JUDGED.getMessage());

    }

    @DisplayName("이미 시간 초과인 Submit은 다시 다른 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyTimeLimitExceeded() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(TIME_LIMIT_EXCEEDED)
                .memory(0)
                .time(0)
                .build();

        제출.updateJudgementResult(judgementResult);

        // when & then
        assertThatThrownBy(() -> 제출.updateJudgementResult(judgementResult))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_JUDGED.getMessage());

    }

    @DisplayName("이미 메모리 초과인 Submit은 다시 다른 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyMemoryLimitExceeded() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(MEMORY_LIMIT_EXCEEDED)
                .memory(0)
                .time(0)
                .build();

        제출.updateJudgementResult(judgementResult);

        // when & then
        assertThatThrownBy(() -> 제출.updateJudgementResult(judgementResult))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_JUDGED.getMessage());

    }

    @DisplayName("이미 틀린 답인 Submit은 다시 다른 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyWrongAnswer() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        LocalDateTime 제출_시간 = LocalDateTime.of(2021, 1, 1, 0, 0);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                제출_시간,
                SubmitVisibility.OPEN);

        JudgementResult judgementResult = JudgementResult.builder()
                .judgementStatus(WRONG_ANSWER)
                .memory(0)
                .time(0)
                .build();

        제출.updateJudgementResult(judgementResult);

        // when & then
        assertThatThrownBy(() -> 제출.updateJudgementResult(judgementResult))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_JUDGED.getMessage());

    }

}