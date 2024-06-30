package kr.co.morandi.backend.defense_management.domain.service;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.session.ExamStatus;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.DefenseSessionRepository;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.domain.model.record.RecordStatus;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Transactional
class SessionServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DefenseSessionRepository defenseSessionRepository;

    @Autowired
    private SessionService sessionService;


    @DisplayName("DailyDefense를 시작했을 때 세션과 Record를 종료할 수 있다.")
    @Test
    void terminateDefense() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);
        Member member = createMember();
        DailyRecord dailyRecord = tryDailyDefense(today, member);
        DefenseSession session = DefenseSession.builder()
                .recordId(dailyRecord.getRecordId())
                .problemNumbers(Set.of(2L))
                .startDateTime(today)
                .endDateTime(today.plusMinutes(1))
                .build();

        final DefenseSession defenseSession = defenseSessionRepository.save(session);

        // when
        sessionService.terminateDefense(defenseSession.getDefenseSessionId());

        // then
        defenseSessionRepository.findById(defenseSession.getDefenseSessionId())
                .ifPresent(s -> assertEquals(s.getExamStatus(), ExamStatus.COMPLETED));

    }

    @DisplayName("Session이 종료된 상태에서 세션을 종료하려고 하면 예외가 발생한다.")
    @Test
    void terminateDefenseWhenSessionTerminated() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);
        Member member = createMember();
        DailyRecord dailyRecord = tryDailyDefense(today, member);
        DefenseSession session = DefenseSession.builder()
                .recordId(dailyRecord.getRecordId())
                .problemNumbers(Set.of(2L))
                .startDateTime(today)
                .endDateTime(today.plusMinutes(1))
                .build();

        final DefenseSession defenseSession = defenseSessionRepository.save(session);
        defenseSession.terminateSession();

        // when & then
        assertThatThrownBy(() -> sessionService.terminateDefense(defenseSession.getDefenseSessionId()))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("이미 종료된 세션입니다.");
    }

    @DisplayName("없는 Session ID로 세션을 종료하려고 하면 예외가 발생한다.")
    @Test
    void terminateDefenseWhenSessionNotFound() {
        // given
        Long notFoundSessionId = 1L;


        // when & then
        assertThatThrownBy(() -> sessionService.terminateDefense(notFoundSessionId))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("세션을 찾을 수 없습니다.");
    }

    @DisplayName("Session이 종료된 상태에서 세션을 종료하려고 하면 예외가 발생한다.")
    @Test
    void terminateDefenseWhenRecordTerminated() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);
        Member member = createMember();
        DailyRecord dailyRecord = tryDailyDefense(today, member);
        DefenseSession session = DefenseSession.builder()
                .recordId(dailyRecord.getRecordId())
                .problemNumbers(Set.of(2L))
                .startDateTime(today)
                .endDateTime(today.plusMinutes(1))
                .build();
        dailyRecord.terminteDefense();

        final DefenseSession defenseSession = defenseSessionRepository.save(session);



        // when & then
        assertThatThrownBy(() -> sessionService.terminateDefense(defenseSession.getDefenseSessionId()))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("이미 종료된 시험 기록입니다.");

    }

    private DailyRecord tryDailyDefense(LocalDateTime today, Member member) {
        final DailyDefense dailyDefense = createDailyDefense(today.toLocalDate());
        final Map<Long, Problem> problem = getProblem(dailyDefense, 2L);

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(today)
                .defense(dailyDefense)
                .member(member)
                .problems(problem)
                .build();

        final DailyRecord savedDailyRecord = dailyRecordRepository.save(dailyRecord);

        final DailyDetail dailyDetail = DailyDetail.builder()
                .member(member)
                .problemNumber(1L)
                .problem(problem.get(1L))
                .records(savedDailyRecord)
                .defense(dailyDefense)
                .build();

        savedDailyRecord.getDetails().add(dailyDetail);

        return dailyRecordRepository.save(savedDailyRecord);
    }

    private Map<Long, Problem> getProblem(DailyDefense dailyDefense, Long problemNumber) {
        return dailyDefense.getDailyDefenseProblems().stream()
                .filter(problem -> Objects.equals(problem.getProblemNumber(), problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }

    private DailyDefense createDailyDefense(LocalDate createdDate) {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));

        return dailyDefenseRepository.save(DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap));
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.builder()
                .baekjoonProblemId(1L)
                .problemTier(B5)
                .build();

        Problem problem2 = Problem.builder()
                .baekjoonProblemId(2L)
                .problemTier(S5)
                .build();

        Problem problem3 = Problem.builder()
                .baekjoonProblemId(3L)
                .problemTier(G5)
                .build();

        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember() {
        return memberRepository.save(Member.builder()
                .email("test")
                .build());
    }
}