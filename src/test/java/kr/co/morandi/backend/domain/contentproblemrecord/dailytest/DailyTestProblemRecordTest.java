package kr.co.morandi.backend.domain.contentproblemrecord.dailytest;

import kr.co.morandi.backend.domain.contentrecord.dailytest.DailyTestRecord;
import kr.co.morandi.backend.domain.contenttype.dailytest.DailyTest;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class DailyTestProblemRecordTest {
    @DisplayName("DailyTestProblemRecord를 생성한다.")
    @Test
    void create() {
        // given
        DailyTest dailyTest = mock(DailyTest.class);
        DailyTestRecord dailyTestRecord = mock(DailyTestRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        DailyTestProblemRecord dailyTestProblemRecord = DailyTestProblemRecord.create(member, problem, dailyTestRecord, dailyTest);

        // then
        assertThat(dailyTestProblemRecord).isNotNull()
                .extracting("member", "problem", "contentType", "contentRecord")
                .contains(member, problem, dailyTest, dailyTestRecord);


    }
    @DisplayName("DailyTestProblemRecord가 생성되면 isSolved는 false이다")
    @Test
    void initialIsSolvedFalse() {
        // given
        DailyTest dailyTest = mock(DailyTest.class);
        DailyTestRecord dailyTestRecord = mock(DailyTestRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        DailyTestProblemRecord dailyTestProblemRecord = DailyTestProblemRecord.create(member, problem, dailyTestRecord, dailyTest);

        // then
        assertThat(dailyTestProblemRecord.getIsSolved()).isFalse();
    }
    @DisplayName("DailyTestProblemRecord가 생성되면 submitCount는 0이다")
    @Test
    void initialSubmitCountIsZero() {
        // given
        DailyTest dailyTest = mock(DailyTest.class);
        DailyTestRecord dailyTestRecord = mock(DailyTestRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        DailyTestProblemRecord dailyTestProblemRecord = DailyTestProblemRecord.create(member, problem, dailyTestRecord, dailyTest);

        // then
        assertThat(dailyTestProblemRecord.getSubmitCount()).isZero();
    }
    @DisplayName("DailyTestProblemRecord가 생성되면 solvedCode는 null이다")
    @Test
    void initialSolvedCodeIsSetToNull() {
        // given
        DailyTest dailyTest = mock(DailyTest.class);
        DailyTestRecord dailyTestRecord = mock(DailyTestRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        DailyTestProblemRecord dailyTestProblemRecord = DailyTestProblemRecord.create(member, problem, dailyTestRecord, dailyTest);

        // then
        assertThat(dailyTestProblemRecord.getSolvedCode())
                .isEqualTo(null);
    }
    private Problem createProblem() {
        return Problem.create(1L, B5, 0L);
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
}