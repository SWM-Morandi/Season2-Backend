package kr.co.morandi.backend.domain.contentproblemrecord.dailytest;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.dailytest.DailyTestRecord;
import kr.co.morandi.backend.domain.contenttype.dailytest.DailyTest;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DailyTestProblemRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }
    @DisplayName("오늘의 문제 테스트 응시 기록이 만들어졌을 때 초기 사용자의 정답 여부는 오답 상태여야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        Member member = makeMember("user");
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), now, dailyTest, member, problems);
        List<ContentProblemRecord> contentProblemRecords = dailyTestRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("isSolved")
                .containsExactlyInAnyOrder(false, false, false);
    }
    @DisplayName("오늘의 문제 테스트 응시 기록이 만들어졌을 때 초기 사용자의 제출 횟수는 모두 0회여야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        Member member = makeMember("user");
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), now, dailyTest, member, problems);
        List<ContentProblemRecord> contentProblemRecords = dailyTestRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L, 0L, 0L);
    }
    @DisplayName("오늘의 문제 테스트 응시 기록이 만들어졌을 때 초기 사용자의 정답 코드는 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        Member member = makeMember("user");
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), now, dailyTest, member, problems);
        List<ContentProblemRecord> contentProblemRecords = dailyTestRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("solvedCode")
                .containsExactlyInAnyOrder(null, null, null);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        List<Problem> problems = List.of(problem1, problem2, problem3);
        problemRepository.saveAll(problems);
        return problems;
    }
    private Member makeMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}