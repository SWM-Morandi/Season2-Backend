package kr.co.morandi.backend.domain.contentrecord.dailytest;

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
    @DisplayName("사용자가 오늘의 문제를 처음 시작할 때 푼 문제수는 0문제이고 전체 문제수는 오늘의 문제 수와 같아야 한다.")
    @Test
    void solvedCountAndProblemCount() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        Member member = makeMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), now, dailyTest, member, problems);

        // then
        assertThat(dailyTestRecord).extracting("solvedCount", "problemCount")
                .contains(0L, dailyTest.getProblemCount());
    }
    @DisplayName("사용자가 오늘의 문제를 시도한 날짜는 오늘의 문제 세트가 만들어졌을 때와 같아야 한다.")
    @Test
    void testDateIsEqual() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        Member member = makeMember("user");

        // when
        DailyTestRecord dailyTestRecord = DailyTestRecord.create(dailyTest.getProblemCount(), now, dailyTest, member, problems);

        // then
        assertThat(dailyTestRecord.getTestDate()).isEqualTo(now);
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