package kr.co.morandi.backend.defense_information.domain.service.dailydefense;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.application.port.out.dailydefense.DailyDefensePort;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;
import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class DailyDefenseGenerationServiceTest extends IntegrationTestSupport {
    
    @Autowired
    private DailyDefenseGenerationService dailyDefenseGenerationService;

    @Autowired
    private DailyDefensePort dailyDefensePort;

    @Autowired
    private ProblemRepository problemRepository;

    @DisplayName("오늘의 문제를 생성할 수 있다.")
    @Test
    void generateDailyDefense() {
        // given
        final List<Problem> problems = createProblems();
        LocalDateTime requestTIme = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        // when
        final boolean result = dailyDefenseGenerationService.generateDailyDefense(requestTIme);

        // then
        assertThat(result).isTrue();

        final DailyDefense nextDaysDailyDefense = dailyDefensePort.findDailyDefense(DAILY, requestTIme.plusDays(1L).toLocalDate());

        assertThat(nextDaysDailyDefense).isNotNull();
        assertThat(nextDaysDailyDefense.getDailyDefenseProblems()).hasSize(5);
    }
    
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B3, 1500L);
        Problem problem2 = Problem.create(2L, S4, 1500L);
        Problem problem3 = Problem.create(3L, S2, 1500L);
        Problem problem4 = Problem.create(4L, G4, 1500L);
        Problem problem5 = Problem.create(5L, G2, 1500L);
        Problem problem6 = Problem.create(6L, B3, 1500L);
        Problem problem7 = Problem.create(7L, S4, 1500L);
        Problem problem8 = Problem.create(8L, S2, 1500L);
        Problem problem9 = Problem.create(9L, G4, 1500L);
        Problem problem10 = Problem.create(10L, G2, 1500L);

        final List<Problem> problemList = List.of(problem1, problem2, problem3, problem4, problem5, problem6, problem7, problem8, problem9, problem10);
        problemList.forEach(Problem::activate);
        return problemRepository.saveAll(problemList);
        
    }
 
}