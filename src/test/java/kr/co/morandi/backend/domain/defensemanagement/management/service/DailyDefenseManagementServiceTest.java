package kr.co.morandi.backend.domain.defensemanagement.management.service;

import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defensemanagement.management.request.StartDailyDefenseServiceRequest;
import kr.co.morandi.backend.domain.defensemanagement.management.response.StartDailyDefenseServiceResponse;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.infrastructure.persistence.defensemanagement.session.DefenseSessionRepository;
import kr.co.morandi.backend.infrastructure.persistence.defensemanagement.sessiondetail.SessionDetailRepository;
import kr.co.morandi.backend.infrastructure.persistence.detail.dailydetail.DailyDetailRepository;
import kr.co.morandi.backend.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.infrastructure.persistence.problem.ProblemRepository;
import kr.co.morandi.backend.infrastructure.persistence.record.dailyrecord.DailyRecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defense.DefenseType.DAILY;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.model.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest
@ActiveProfiles("test")
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
        dailyDefenseManagementService.startDailyDefense(request, member, requestTime);

        StartDailyDefenseServiceRequest retryRequest = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        LocalDateTime retryRequestTime = LocalDateTime.of(2021, 10, 2, 12, 0, 0);

        // when
        final StartDailyDefenseServiceResponse response = dailyDefenseManagementService.startDailyDefense(retryRequest, member, retryRequestTime);


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
        dailyDefenseManagementService.startDailyDefense(request, member, requestTime);

        StartDailyDefenseServiceRequest retryRequest = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();

        LocalDateTime retryRequestTime = LocalDateTime.of(2021, 10, 1, 12, 0, 0);
        // when
        final StartDailyDefenseServiceResponse response = dailyDefenseManagementService.startDailyDefense(retryRequest, member, retryRequestTime);

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
        dailyDefenseManagementService.startDailyDefense(request, member, requestTime);


        LocalDateTime retryRequestTime = LocalDateTime.of(2021, 10, 1, 12, 0, 0);
        // when
        final StartDailyDefenseServiceResponse response = dailyDefenseManagementService.startDailyDefense(request, member, retryRequestTime);

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
        final StartDailyDefenseServiceResponse response = dailyDefenseManagementService.startDailyDefense(request, member, requestTime);

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