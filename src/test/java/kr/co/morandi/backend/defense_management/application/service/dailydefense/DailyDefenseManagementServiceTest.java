package kr.co.morandi.backend.defense_management.application.service.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDetailRepository;
import kr.co.morandi.backend.defense_management.application.request.session.StartDailyDefenseServiceRequest;
import kr.co.morandi.backend.defense_management.application.response.session.StartDailyDefenseResponse;
import kr.co.morandi.backend.defense_management.application.service.session.DailyDefenseManagementService;
import kr.co.morandi.backend.defense_management.application.service.timer.DefenseTimerService;
import kr.co.morandi.backend.defense_management.domain.event.DefenseStartTimerEvent;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.DefenseSessionRepository;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.SessionDetailRepository;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.application.response.problemcontent.ProblemContent;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.adapter.problemcontent.ProblemContentAdapter;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;
import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@RecordApplicationEvents
class DailyDefenseManagementServiceTest {

    @Autowired
    private DailyDefenseManagementService dailyDefenseManagementService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DailyDetailRepository dailyDetailRepository;

    @Autowired
    private DefenseSessionRepository defenseSessionRepository;

    @Autowired
    private SessionDetailRepository sessionDetailRepository;

    @MockBean
    private ProblemContentAdapter problemContentAdapter;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @MockBean
    private DefenseTimerService defenseTimerService;

    @BeforeEach
    void setUp() {
        Map<Long, ProblemContent> problemContentMap = Map.of(
                1L, ProblemContent.builder()
                        .baekjoonProblemId(1000L)
                        .title("test")
                        .build(),
                2L, ProblemContent.builder()
                        .baekjoonProblemId(2000L)
                        .title("test2")
                        .build(),
                3L, ProblemContent.builder()
                        .baekjoonProblemId(3000L)
                        .title("test3")
                        .build()
        );
        Mockito.when(problemContentAdapter.getProblemContents(anyList()))
                .thenReturn(problemContentMap);
    }

    @AfterEach
    void tearDown() {
        sessionDetailRepository.deleteAll();
        defenseSessionRepository.deleteAllInBatch();
        dailyDetailRepository.deleteAllInBatch();
        dailyRecordRepository.deleteAllInBatch();
        dailyDefenseProblemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("오늘의 문제가 시작되다가 롤백되면 타이머 이벤트가 실행되지 않는다.")
    @Test
    void eventPublishWhenStartDailyDefenseWhenRollback() {
        // given
        Member member = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest request = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        // when
        transactionTemplate.execute(status -> {
            dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), requestTime);
            status.setRollbackOnly(); // Force rollback
            return null;
        });

        // then
        assertThat(applicationEvents.stream(DefenseStartTimerEvent.class))
                .hasSize(1)
                .anySatisfy(event -> {
                    assertAll(
                            () -> assertThat(event.getSessionId()).isNotNull(),
                            () -> assertThat(event.getStartDateTime()).isNotNull(),
                            () -> assertThat(event.getEndDateTime()).isNotNull()
                    );
                });

        verify(defenseTimerService, never()).startDefenseTimer(any(), any(), any());
    }

    @DisplayName("오늘의 문제가 시작될 때 타이머 이벤트를 발행한다.")
    @Test
    void eventPublishWhenStartDailyDefense() {
        // given
        Member member = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest request = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        // when
        dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), requestTime);

        // then
        assertThat(applicationEvents.stream(DefenseStartTimerEvent.class))
            .hasSize(1)
            .anySatisfy(event -> {
                assertAll(
                        () -> assertThat(event.getSessionId()).isNotNull(),
                        () -> assertThat(event.getStartDateTime()).isNotNull(),
                        () -> assertThat(event.getEndDateTime()).isNotNull()
                );
            });
        verify(defenseTimerService, times(1)).startDefenseTimer(any(), any(), any());
    }
    @DisplayName("전날 시작했던 시험이 안 끝났더라도 오늘의 문제를 시도하면 해당하는 날짜의 문제를 제공한다.")
    @Test
    void retryDailyDefenseWhenDayPassed() {
        // given
        Member member = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "10월 1일 오늘의 문제 테스트");
        createDailyDefense(LocalDate.of(2021, 10, 2), "10월 2일 오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest request = StartDailyDefenseServiceRequest.builder()
                .problemNumber(1L)
                .build();
        dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), requestTime);

        StartDailyDefenseServiceRequest retryRequest = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        LocalDateTime retryRequestTime = LocalDateTime.of(2021, 10, 2, 12, 0, 0);

        // when
        final StartDailyDefenseResponse response = dailyDefenseManagementService.startDailyDefense(retryRequest, member.getMemberId(), retryRequestTime);


        // then
        assertAll(
                () -> assertThat(response).isNotNull()
                        .extracting("lastAccessTime", "contentName", "defenseType")
                        .contains(retryRequestTime, "10월 2일 오늘의 문제 테스트", DAILY),

                () -> assertThat(response.getDefenseProblems()).hasSize(1)
                        .extracting("problemNumber", "baekjoonProblemId", "isCorrect")
                        .contains(
                                tuple(2L, 2000L, false)
                        )
        );

    }

    @DisplayName("시도하던 오늘의 문제 외 다른 문제를 시도하면 새로 시도한 문제를 제공한다.")
    @Test
    void retryDailyDefenseWithOtherProblem() {
        // given
        Member member = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest request = StartDailyDefenseServiceRequest.builder()
                .problemNumber(1L)
                .build();
        dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), requestTime);

        StartDailyDefenseServiceRequest retryRequest = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        LocalDateTime retryRequestTime = LocalDateTime.of(2021, 10, 1, 12, 0, 0);
        // when
        final StartDailyDefenseResponse response = dailyDefenseManagementService.startDailyDefense(retryRequest, member.getMemberId(), retryRequestTime);

        // then
        assertAll(
                () -> assertThat(response).isNotNull()
                    .extracting("lastAccessTime")
                    .isEqualTo(retryRequestTime),

                () -> assertThat(response.getDefenseProblems()).hasSize(1)
                    .extracting("problemNumber", "baekjoonProblemId", "isCorrect")
                    .contains(
                            tuple(2L, 2000L, false)
                    )
        );

    }

    @DisplayName("시도하던 오늘의 문제에 대해 다시 시도하면 기존의 문제를 다시 제공한다. (다른 문제 시도나, tempCode 등의 기록이 없으면 기존 시간 그대로 기록돼있음")
    @Test
    void retryDailyDefense() {
        // given
        Member member = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest request = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();
        dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), requestTime);


        LocalDateTime retryRequestTime = LocalDateTime.of(2021, 10, 1, 12, 0, 0);
        // when
        final StartDailyDefenseResponse response = dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), retryRequestTime);

        // then
        assertAll(
                () -> assertThat(response).isNotNull()
                        .extracting("lastAccessTime")
                        .isEqualTo(requestTime),

                () ->  assertThat(response.getDefenseProblems()).hasSize(1)
                        .extracting("problemNumber", "baekjoonProblemId", "isCorrect")
                        .contains(
                                tuple(2L, 2000L, false)
                        )
        );
    }

    @DisplayName("오늘의 문제를 시작할 수 있다.")
    @Test
    void startDailyDefense() {
        // given
        Member member = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest request = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        // when
        final StartDailyDefenseResponse response = dailyDefenseManagementService.startDailyDefense(request, member.getMemberId(), requestTime);

        // then

        assertAll(
                () -> assertThat(response).isNotNull()
                    .extracting("lastAccessTime")
                    .isEqualTo(requestTime),

                () -> assertThat(response.getDefenseProblems()).hasSize(1)
                    .extracting("problemNumber", "baekjoonProblemId", "isCorrect")
                    .contains(
                            tuple(2L, 2000L, false)
                        )
        );

    }

    private DailyDefense createDailyDefense(LocalDate createdDate, String contentName) {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return dailyDefenseRepository.save(DailyDefense.create(createdDate, contentName, problemMap));
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1000L, B5, 0L);
        Problem problem2 = Problem.create(2000L, S5, 0L);
        Problem problem3 = Problem.create(3000L, G5, 0L);

        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember() {
        return memberRepository.save(Member.create("test", "test" + "@gmail.com", GOOGLE, "test", "test"));
    }


}