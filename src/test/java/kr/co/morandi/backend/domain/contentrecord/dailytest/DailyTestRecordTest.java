package kr.co.morandi.backend.domain.contentrecord.dailytest;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contenttype.dailytest.DailyTest;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DailyTestRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }
    @DisplayName("오늘의 문제 기록이 만들어졌을 때 푼 문제 수는 0문제 이어야 한다.")
    @Test
    void solvedCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime createdTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyTest dailyTest = DailyTest.create(createdTime, "오늘의 문제 테스트", problems);
        Member member = createMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), startTime, dailyTest, member, problems);

        // then
        assertThat(dailyTestRecord.getSolvedCount()).isZero();
    }
    @DisplayName("오늘의 문제 기록이 만들어졌을 때 전체 문제 수는 오늘의 문제에 출제된 문제 수와 같아야 한다.")
    @Test
    void problemCountIsEqual() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime createdTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyTest dailyTest = DailyTest.create(createdTime, "오늘의 문제 테스트", problems);
        Member member = createMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), startTime, dailyTest, member, problems);

        // then
        assertThat(dailyTestRecord.getProblemCount()).isEqualTo(dailyTest.getProblemCount());
    }
    @DisplayName("오늘의 문제 기록이 만들어진 시점이 문제가 출제된 시점에서 하루 이상 넘어가면 예외가 발생한다.")
    @Test
    void recordCreatedWithinOneDay() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime createdTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        DailyTest dailyTest = DailyTest.create(createdTime, "오늘의 문제 테스트", problems);
        Member member = createMember("user");
        LocalDateTime falseStartTime = LocalDateTime.of(2024, 3, 2, 0, 0, 0);
        LocalDateTime trueStartTime = LocalDateTime.of(2024, 3, 1, 23, 59, 59);
        // when & then
        assertThatThrownBy(() -> DailyTestRecord.create(dailyTest.getProblemCount(), falseStartTime, dailyTest, member, problems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("오늘의 문제 기록은 출제 시점으로부터 하루 이내에 생성되어야 합니다.");

        assertNotNull(DailyTestRecord.create(dailyTest.getProblemCount(), trueStartTime, dailyTest, member, problems));
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 정답 여부는 모두 오답 상태여야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime createdTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyTest dailyTest = DailyTest.create(createdTime, "오늘의 문제 테스트", problems);
        Member member = createMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), startTime, dailyTest, member, problems);
        List<ContentProblemRecord> contentProblemRecords = dailyTestRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("isSolved")
                .containsExactlyInAnyOrder(false, false, false);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 제출 횟수는 모두 0회여야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime createdTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyTest dailyTest = DailyTest.create(createdTime, "오늘의 문제 테스트", problems);
        Member member = createMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), startTime, dailyTest, member, problems);
        List<ContentProblemRecord> contentProblemRecords = dailyTestRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L, 0L, 0L);
    }
    @DisplayName("오늘의 문제 테스트 기록이 만들어졌을 때 세부 문제들의 정답 코드는 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime createdTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyTest dailyTest = DailyTest.create(createdTime, "오늘의 문제 테스트", problems);
        Member member = createMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), startTime , dailyTest, member, problems);
        List<ContentProblemRecord> contentProblemRecords = dailyTestRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("solvedCode")
                .containsExactlyInAnyOrder(null, null, null);
    }

    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}