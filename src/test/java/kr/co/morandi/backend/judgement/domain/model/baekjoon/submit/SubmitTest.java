package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.factory.TestDefenseFactory;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.factory.TestProblemFactory;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.judgement.domain.model.submit.Submit;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class SubmitTest {

    /*
    *  추상 클래스에 관한 공통 메서드 테스트 작성을 위해
    *  Submit 클래스를 상속받는 SubmitImpl 클래스를 생성했습니다.
    */
    static class SubmitTestImpl extends Submit {
        public SubmitTestImpl(Member member, Detail detail, SubmitCode submitCode, SubmitVisibility submitVisibility, Integer trialNumber) {
            super(member, detail, submitCode, submitVisibility, trialNumber);
        }
    }

    @DisplayName("trailNumber가 null일 때 Submit을 생성하려고 하면 예외가 발생한다.")
    @Test
    void trialNumberIsNull() {
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

        // when & then
        assertThatThrownBy(
                () -> new SubmitTestImpl(사용자,
                        dailyRecord.getDetail(1L),
                        제출할_코드,
                        SubmitVisibility.OPEN,
                        null)
        ).isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.TRIAL_NUMBER_IS_NULL.getMessage());

    }

    @DisplayName("음수인 trailNumber로 Submit을 생성하려고 하면 예외가 발생한다.")
    @Test
    void trialNumberIsNegative() {
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

        // when & then
        assertThatThrownBy(
                () -> new SubmitTestImpl(사용자,
                        dailyRecord.getDetail(1L),
                        제출할_코드,
                        SubmitVisibility.OPEN,
                        -1)
        ).isInstanceOf(MorandiException.class)
                .hasMessage(SubmitErrorCode.TRIAL_NUMBER_IS_NEGATIVE.getMessage());

    }

    @DisplayName("Submit 후 정답 상태로 변경할 수 있다.")
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

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                SubmitVisibility.OPEN,
                1);

        // when
        제출.updateStatusToAccepted(300, 30);

        // then
        assertThat(제출)
                .extracting("judgementResult.judgementStatus", "judgementResult.memory", "judgementResult.time", "trialNumber")
                .contains(ACCEPTED, 300, 30, 1);

    }

    @DisplayName("이미 정답상태인 Submit은 다시 정답상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWhenAlreadyAccepted() {
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

        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                SubmitVisibility.OPEN,
                1);
        제출.updateStatusToAccepted(300, 30);


        // when & then
        assertThatThrownBy(() -> 제출.updateStatusToAccepted(300, 30))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.ALREADY_ACCEPTED.getMessage());

    }

    @DisplayName("적절하지 않은 메모리 값으로 정답 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWithInvalidMemory() {
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
        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                SubmitVisibility.OPEN,
                1);


        // when & then
        assertThatThrownBy(() -> 제출.updateStatusToAccepted(-300, 30))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.MEMORY_IS_NEGATIVE.getMessage());

    }

    @DisplayName("적절하지 않은 시간 값으로 정답 상태로 변경할 수 없다.")
    @Test
    void updateStatusToAcceptedWithInvalidTime() {
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
        Submit 제출 = new SubmitTestImpl(사용자,
                dailyRecord.getDetail(1L),
                제출할_코드,
                SubmitVisibility.OPEN,
                1);


        // when & then
        assertThatThrownBy(() -> 제출.updateStatusToAccepted(300, -30))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementResultErrorCode.TIME_IS_NEGATIVE.getMessage());

    }

}