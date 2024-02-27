package kr.co.morandi.backend.domain.contenttype.dailytest;

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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DailyTestProblemsTest {

    @Autowired
    private ProblemRepository problemRepository;
    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
    }
    @DisplayName("오늘의 문제가 만들어졌을 때, 초기의 문제 제출횟수는 0이어야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        List<DailyTestProblems> dailyTestProblemsList = dailyTest.getDailyTestProblemsList();

        // when & then
        assertThat(dailyTestProblemsList)
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L, 0L, 0L);
    }

    @DisplayName("오늘의 문제가 만들어졌을 때, 초기 정답자 수는 0이어야 한다.")
    @Test
    void solvedCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);
        List<DailyTestProblems> dailyTestProblemsList = dailyTest.getDailyTestProblemsList();

        // when & then
        assertThat(dailyTestProblemsList)
                .extracting("solvedCount")
                .containsExactlyInAnyOrder(0L, 0L, 0L);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        List<Problem> problems = List.of(problem1, problem2, problem3);
        problemRepository.saveAll(problems);
        return problems;
    }
}