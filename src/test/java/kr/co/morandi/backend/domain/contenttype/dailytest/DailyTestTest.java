package kr.co.morandi.backend.domain.contenttype.dailytest;

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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DailyTestTest {

    @Autowired
    private ProblemRepository problemRepository;
    @AfterEach
    void tearDown() {
        problemRepository.deleteAllInBatch();
    }
    @DisplayName("오늘의 문제 세트가 만들어진 시점에서 시도한 사람의 수는 0명 이어야 한다.")
    @Test
    public void attemptCountIsZero() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();

        // when
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);

        // then
        assertThat(dailyTest.getAttemptCount()).isZero();
    }
    @DisplayName("오늘의 문제가 만들어진 시점에 등록된 날짜는 만들어진 시점과 같아야 한다.")
    @Test
    public void testDateEqualNow() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();

        // when
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);

        // then
        assertThat(dailyTest.getDate()).isEqualTo(now);
    }
    @DisplayName("오늘의 문제가 만들어진 이름은 일치해야한다.")
    @Test
    public void contentNameIsEqual() {
        // given
        List<Problem> problems = createProblems();
        LocalDateTime now = LocalDateTime.now();

        // when
        DailyTest dailyTest = DailyTest.create(now, "오늘의 문제 테스트", problems);

        // then
        assertThat(dailyTest.getContentName()).isEqualTo("오늘의 문제 테스트");
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