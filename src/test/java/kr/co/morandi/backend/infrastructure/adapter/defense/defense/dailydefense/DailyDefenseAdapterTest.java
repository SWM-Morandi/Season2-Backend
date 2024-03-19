package kr.co.morandi.backend.infrastructure.adapter.defense.defense.dailydefense;

import kr.co.morandi.backend.application.port.out.defense.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.DefenseType.DAILY;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class DailyDefenseAdapterTest {

    @Autowired
    private DailyDefensePort dailyDefensePort;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @AfterEach
    void tearDown() {
        dailyDefenseProblemRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
    }

    @DisplayName("DailyDefense를 찾을 수 있다.")
    @Test
    void findTodayDailyDefense() {
        // given
        LocalDate date = LocalDate.of(2021, 1, 1);
        DailyDefense dailyDefense = createDailyDefense(date);

        // when
        final DailyDefense findDailyDefense = dailyDefensePort.findDailyDefense(DAILY, date);

        // then
        assertThat(findDailyDefense).isNotNull()
                .extracting("date", "contentName", "problemCount")
                .contains(dailyDefense.getDate(), dailyDefense.getContentName(), dailyDefense.getProblemCount());

    }
    @DisplayName("DailyDefense가 존재하지 않을 때 예외를 던진다.")
    @Test
    void exceptionWhenTodayDailyDefenseNotExists() {
        // given
        LocalDate createdDate = LocalDate.of(2021, 1, 1);
        createDailyDefense(createdDate);
        LocalDate date = LocalDate.of(2021, 1, 2);

        // when & then
        assertThatThrownBy(() -> dailyDefensePort.findDailyDefense(DAILY, date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("DailyDefense가 존재하지 않습니다");

    }

    private DailyDefense createDailyDefense(LocalDate date) {
        List<Problem> problems = createProblems();
        return dailyDefenseRepository.save(DailyDefense.create(date, "오늘의 문제 테스트", problems));
    }

    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
}