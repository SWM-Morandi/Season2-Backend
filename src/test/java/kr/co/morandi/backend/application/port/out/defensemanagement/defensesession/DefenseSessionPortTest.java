package kr.co.morandi.backend.application.port.out.defensemanagement.defensesession;

import kr.co.morandi.backend.defense_management.application.port.out.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.DefenseSessionRepository;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;
import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DefenseSessionPortTest {

    @Autowired
    private DefenseSessionPort defenseSessionPort;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @Autowired
    private DefenseSessionRepository defenseSessionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        defenseSessionRepository.deleteAll();
        dailyDefenseProblemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("DailyDefense 세션을 조회할 수 있다.")
    @Test
    void findDailyDefenseSession() {
        // given
        final Member member = createMember();
        final DailyDefense dailyDefense = createDailyDefense();
        LocalDateTime startTime = LocalDateTime.of(2021, 10, 1, 0, 0);

        Long recordId = 1L;
        final DefenseSession defenseSession = DefenseSession.startSession(member, recordId, DAILY, Set.of(2L), startTime, dailyDefense.getEndTime(startTime));
        defenseSessionPort.saveDefenseSession(defenseSession);

        // when
        final Optional<DefenseSession> maybeDefenseSession = defenseSessionPort.findTodaysDailyDefenseSession(member, startTime.plusMinutes(1));

        // then
        assertThat(maybeDefenseSession).isPresent()
                .get()
                .extracting(DefenseSession::getRecordId)
                .isEqualTo(recordId);

        final DefenseSession session = maybeDefenseSession.get();
        assertThat(session.getSessionDetails()).hasSize(1)
                .extracting("problemNumber")
                .contains(
                        2L
                );

    }
    private Map<Long, Problem> getProblem(DailyDefense dailyDefense, Long problemNumber) {
        return dailyDefense.getDailyDefenseProblems().stream()
                .filter(problem -> Objects.equals(problem.getProblemNumber(), problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }

    private DailyDefense createDailyDefense() {
        LocalDate createdDate = LocalDate.of(2021, 10, 1);
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return dailyDefenseRepository.save(DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap));
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);

        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember() {
        return memberRepository.save(Member.create("test", "test" + "@gmail.com", GOOGLE, "test", "test"));
    }

}