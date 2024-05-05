package kr.co.morandi.backend.defense_information.application.mapper.dailydefense;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
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
import static org.assertj.core.groups.Tuple.tuple;


@ActiveProfiles("test")
class DailyDefenseInfoMapperTest {

    @DisplayName("시도한 적이 있는 DailyDefense Response DTO를 반환할 수 있다.")
    @Test
    void ofNonAttempted() {
        // given
        DailyDefense dailyDefense = createDailyDefense();

        // when
        DailyDefenseInfoResponse response = DailyDefenseInfoMapper.fromNonAttempted(dailyDefense);

        // then
        assertThat(response)
                .extracting("defenseName", "problemCount", "attemptCount")
                .contains(dailyDefense.getContentName(), dailyDefense.getDailyDefenseProblems().size(), 0L);

        assertThat(response.getProblems())
                .extracting("problemNumber", "baekjoonProblemId", "difficulty", "solvedCount", "submitCount", "isSolved")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, B5, 0L, 0L, null),
                        tuple(2L, 2L, S5, 0L, 0L, null),
                        tuple(3L, 3L, G5, 0L, 0L, null)
                );


    }

    @DisplayName("시도한 적이 있는 DailyDefense Response DTO를 반환할 수 있다.")
    @Test
    void ofAttempted() {
        // given
        DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        Member member = createMember("user");
        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);


        // when
        DailyDefenseInfoResponse response = DailyDefenseInfoMapper.ofAttempted(dailyDefense, dailyRecord);

        // then
        assertThat(response)
                .extracting("defenseName", "problemCount", "attemptCount")
                .contains(dailyDefense.getContentName(), dailyDefense.getDailyDefenseProblems().size(), 1L);

        assertThat(response.getProblems())
                .extracting("problemNumber", "baekjoonProblemId", "difficulty", "solvedCount", "submitCount", "isSolved")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, B5, 0L, 0L, false),
                        tuple(2L, 2L, S5, 0L, 0L, false),
                        tuple(3L, 3L, G5, 0L, 0L, false)
                );
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
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return List.of(problem1, problem2, problem3);
    }

    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }

}