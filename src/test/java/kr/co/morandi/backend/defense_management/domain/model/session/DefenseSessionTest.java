package kr.co.morandi.backend.defense_management.domain.model.session;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.CPP;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class DefenseSessionTest {

    @DisplayName("문제 번호를 가지고 있는지 확인한다.")
    @Test
    void hasTriedProblem() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());
        Map<Long, Problem> problems = getProblems(dailyDefense, 1L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        DefenseSession defenseSession = DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), problems.keySet(), startTime, dailyDefense.getEndTime(startTime));

        // when
        final boolean result = defenseSession.hasTriedProblem(1L);

        // then
        assertThat(result).isTrue();

    }

    @DisplayName("문제 번호를 가지고 있지 않으면 false를 반환한다.")
    @Test
    void hasNotTriedProblem() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());
        Map<Long, Problem> problems = getProblems(dailyDefense, 1L);
        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        DefenseSession defenseSession = DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), problems.keySet(), startTime, dailyDefense.getEndTime(startTime));

        // when
        final boolean result = defenseSession.hasTriedProblem(2L);

        // then
        assertThat(result).isFalse();

    }

    @DisplayName("Session을 생성하면 TempCode까지 생성된다.")
    @Test
    void sessionWithTempCode() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());

        Map<Long, Problem> problems = getProblems(dailyDefense, 1L);

        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // when
        DefenseSession defenseSession = DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), problems.keySet(), startTime, dailyDefense.getEndTime(startTime));


        // then
        assertThat(defenseSession).isNotNull();
        assertThat(defenseSession.getSessionDetails()).hasSize(1)
                .extracting("problemNumber")
                .containsExactly(1L);

        SessionDetail sessionDetail = defenseSession.getSessionDetails().iterator().next();

        assertThat(sessionDetail.getTempCodes())
                .isNotEmpty();

        TempCode tempCode = sessionDetail.getTempCodes().iterator().next();
        assertThat(tempCode.getLanguage())
                .isEqualTo(CPP);
    }

    @DisplayName("문제를 추가하면 Detail이 추가된다.")
    @Test
    void tryMoreProblem() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());

        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        DefenseSession defenseSession = DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), problems.keySet(), startTime, dailyDefense.getEndTime(startTime));

        // when
        defenseSession.tryMoreProblem(3L, startTime.plusMinutes(1));

        // then
        assertThat(defenseSession.getSessionDetails()).hasSize(2)
                .extracting("problemNumber")
                .containsExactlyInAnyOrder(2L, 3L);

    }

    @DisplayName("Record에 따라 시험 세션을 시작하면 마지막 접근 문제 번호가 가장 첫 번째 숫자로 저장된다.")
    @Test
    void startSessionWithRecord() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());

        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // when
        DefenseSession defenseSession = DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), problems.keySet(), startTime, dailyDefense.getEndTime(startTime));

        // then
        assertThat(defenseSession.getLastAccessProblemNumber()).isEqualTo(2L);
    }

    @DisplayName("문제 번호 없이 Session을 시작하려 하면 예외를 발생한다.")
    @Test
    void startSessionWithoutProblemNumber() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());

        Map<Long, Problem> problems = getProblems(dailyDefense, null);

        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // when & then
        assertThatThrownBy(() -> DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), new HashSet<>(), startTime, dailyDefense.getEndTime(startTime)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("문제 번호가 없습니다.");

    }

    @DisplayName("Record에 따라 시험 세션을 시작할 수 있다.")
    @Test
    void startSession() {
        // given
        Member member = createMember();
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        DailyDefense dailyDefense = createDailyDefense(startTime.toLocalDate());

        Map<Long, Problem> problems = getProblems(dailyDefense, 2L);

        DailyRecord dailyRecord = DailyRecord.tryDefense(startTime, dailyDefense, member, problems);

        // when
        DefenseSession defenseSession = DefenseSession.startSession(member, dailyRecord.getRecordId(), dailyDefense.getDefenseType(), problems.keySet(), startTime, dailyDefense.getEndTime(startTime));

        // then
        assertThat(defenseSession).isNotNull();
    }

    private Member createMember() {
        return Member.create("nickname", "email", GOOGLE, "imageURL", "description");
    }
    private DailyDefense createDailyDefense(LocalDate createdDate) {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return List.of(problem1, problem2, problem3);
    }
    private Map<Long, Problem> getProblems(DailyDefense DailyDefense, Long problemNumber) {
        return DailyDefense.getDailyDefenseProblems().stream()
                .filter(p -> p.getProblemNumber().equals(problemNumber))
                .collect(Collectors.toMap(DailyDefenseProblem::getProblemNumber, DailyDefenseProblem::getProblem));
    }

}