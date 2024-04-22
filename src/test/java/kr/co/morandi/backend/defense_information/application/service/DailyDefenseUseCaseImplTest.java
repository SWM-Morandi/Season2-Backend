package kr.co.morandi.backend.defense_information.application.service;

import kr.co.morandi.backend.defense_information.application.dto.response.DailyDefenseInfoResponse;
import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import kr.co.morandi.backend.defense_information.domain.service.defense.ProblemGenerationService;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_record.application.port.out.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DailyDefenseUseCaseImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseUseCase dailyDefenseUseCase;

    @Autowired
    private ProblemGenerationService problemGenerationService;

    @Autowired
    private DailyRecordPort dailyRecordPort;

    @DisplayName("사용자가 없을 때 DailyDefense 정보를 조회하면 isSolved는 null로 반환한다.")
    @Test
    void getDailyDefenseInfo() {
        // given
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 12, 0, 0);

        // when
        final DailyDefenseInfoResponse response = dailyDefenseUseCase.getDailyDefenseInfo(null, requestTime);

        // then
        assertAll(
                () -> assertThat(response).isNotNull()
                .extracting("defenseName", "problemCount", "attemptCount")
                .contains("오늘의 문제 테스트", 3, 0L),

                () ->  assertThat(response.getProblems()).hasSize(3)
                .extracting("problemNumber", "baekjoonProblemId", "difficulty", "solvedCount", "submitCount", "isSolved")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1000L, B5, 0L, 0L, null),
                        tuple(2L, 2000L, S5, 0L, 0L, null),
                        tuple(3L, 3000L, G5, 0L, 0L, null)
                )
        );
    }

    @DisplayName("사용자가 있을 때 응시 기록도 있다면 DailyDefense 정보를 조회하면 isSolved는 True/False를 포함하여 반환한다.")
    @Test
    void getDailyDefenseInfoWithMemberAndRecord() {
        // given

        final DailyDefense dailyDefense = createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");
        Member member = createMember();
        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 12, 0, 0);

        final DailyRecord dailyRecord = createDailyRecord(dailyDefense, member, 2L, requestTime);
        dailyRecord.solveProblem(2L, "example", requestTime.plusHours(1));
        dailyRecordPort.saveDailyRecord(dailyRecord);

        // when
        final DailyDefenseInfoResponse response = dailyDefenseUseCase.getDailyDefenseInfo(member.getMemberId(), requestTime);

        // then
        assertAll(
                () -> assertThat(response).isNotNull()
                        .extracting("defenseName", "problemCount", "attemptCount")
                        .contains("오늘의 문제 테스트", 3, 1L),

                () ->  assertThat(response.getProblems()).hasSize(3)
                        .extracting("problemNumber", "baekjoonProblemId", "difficulty", "solvedCount", "submitCount", "isSolved")
                        .containsExactlyInAnyOrder(
                                tuple(1L, 1000L, B5, 0L, 0L, false),
                                tuple(2L, 2000L, S5, 0L, 0L, true),
                                tuple(3L, 3000L, G5, 0L, 0L, false)
                        )
        );
    }

    // 시험에 응시하는 메소드
    private DailyRecord createDailyRecord(DailyDefense dailyDefense, Member member, Long problemNumber, LocalDateTime requestTIme) {
        final Map<Long, Problem> tryingProblem = dailyDefense.getTryingProblem(problemNumber, problemGenerationService);
        final DailyRecord dailyRecord = DailyRecord.tryDefense(requestTIme, dailyDefense, member, tryingProblem);
        dailyRecord.tryMoreProblem(dailyDefense.getTryingProblem(3L, problemGenerationService));

        return dailyRecordPort.saveDailyRecord(dailyRecord);
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