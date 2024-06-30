package kr.co.morandi.backend.defense_record.domain.model.record;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class RecordTest {

    @DisplayName("ProblemNumber로 Problem을 찾아올 수 있다.")
    @Test
    void getProblem() {
        DailyDefense 오늘의_문제 = createDailyDefense();
        LocalDateTime 시험_시작_시간 = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member 시험_보려는_사용자 = createMember("user");

        Long 문제_번호 = 1L;
        Map<Long, Problem> problems = getProblems(오늘의_문제, 문제_번호);

        final DailyRecord dailyRecord = DailyRecord.tryDefense(시험_시작_시간, 오늘의_문제, 시험_보려는_사용자, problems);

        // when
        final Problem problem = dailyRecord.getProblem(문제_번호);

        // then
        assertThat(problem.getBaekjoonProblemId())
                .isEqualTo(문제_번호);

    }

    @DisplayName("Invalid ProblemNumber로 Problem을 찾으려고 하면 예외가 발생한다.")
    @Test
    void getProblemWithInvalidProblemNumber() {
        // given
        DailyDefense 오늘의_문제 = createDailyDefense();
        LocalDateTime 시험_시작_시간 = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member 시험_보려는_사용자 = createMember("user");
        Long 문제_번호 = 1L;
        Map<Long, Problem> problems = getProblems(오늘의_문제, 문제_번호);

        final DailyRecord dailyRecord = DailyRecord.tryDefense(시험_시작_시간, 오늘의_문제, 시험_보려는_사용자, problems);

        Long 잘못된_문제_번호 = 2L;

        // when & then
        assertThatThrownBy(() -> dailyRecord.getProblem(잘못된_문제_번호))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("해당 번호의 문제 풀이 기록을 찾을 수 없습니다.");

    }
    @DisplayName("ProblemNumber로 Detail을 찾아올 수 있다.")
    @Test
    void getDetail() {
        // given
        DailyDefense 오늘의_문제 = createDailyDefense();
        LocalDateTime 시험_시작_시간 = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member 시험_보려는_사용자 = createMember("user");

        Long 문제_번호 = 1L;
        Map<Long, Problem> problems = getProblems(오늘의_문제, 문제_번호);

        final DailyRecord dailyRecord = DailyRecord.tryDefense(시험_시작_시간, 오늘의_문제, 시험_보려는_사용자, problems);

        // when
        final DailyDetail detail = dailyRecord.getDetail(문제_번호);

        // then
        assertThat(detail.getProblem().getBaekjoonProblemId())
                .isEqualTo(문제_번호);

    }

    @DisplayName("Invalid ProblemNumber로 Detail을 찾으려고 하면 예외가 발생한다.")
    @Test
    void getDetailWithInvalidProblemNumber() {
        // given
        DailyDefense 오늘의_문제 = createDailyDefense();
        LocalDateTime 시험_시작_시간 = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member 시험_보려는_사용자 = createMember("user");
        Long 문제_번호 = 1L;
        Map<Long, Problem> problems = getProblems(오늘의_문제, 문제_번호);

        final DailyRecord dailyRecord = DailyRecord.tryDefense(시험_시작_시간, 오늘의_문제, 시험_보려는_사용자, problems);

        Long 잘못된_문제_번호 = 2L;

        // when & then
        assertThatThrownBy(() -> dailyRecord.getDetail(잘못된_문제_번호))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("해당 번호의 문제 풀이 기록을 찾을 수 없습니다.");

    }

    private Map<Long, Problem> getProblems(DailyDefense DailyDefense, Long problemNumber) {
        return DailyDefense.getDailyDefenseProblems().stream()
                .filter(p -> p.getProblemNumber().equals(problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }
    private DailyDefense createDailyDefense() {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p -> problemNumber.getAndIncrement(), problem -> problem));
        LocalDate createdDate = LocalDate.of(2024, 3, 1);
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.builder()
                .baekjoonProblemId(1L)
                .problemTier(B5)
                .solvedCount(0L)
                .build();
        Problem problem2 = Problem.builder()
                .baekjoonProblemId(2L)
                .problemTier(S5)
                .solvedCount(0L)
                .build();
        Problem problem3 = Problem.builder()
                .baekjoonProblemId(3L)
                .problemTier(G5)
                .solvedCount(0L)
                .build();
        return List.of(problem1, problem2, problem3);
    }
    private Member createMember(String name) {
        return Member.builder()
                .email(name + "@gmail.com")
                .socialType(GOOGLE)
                .nickname(name)
                .build();

    }

}