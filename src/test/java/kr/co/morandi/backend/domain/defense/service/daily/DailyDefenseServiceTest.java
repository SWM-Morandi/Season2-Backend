package kr.co.morandi.backend.domain.defense.service.daily;

import kr.co.morandi.backend.domain.defense.dailydefense.model.DailyDefense;
import kr.co.morandi.backend.domain.defense.dailydefense.service.DailyDefenseService;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseProblemRepository;
import kr.co.morandi.backend.infrastructure.persistence.defense.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.infrastructure.persistence.detail.dailydetail.DailyDetailRepository;
import kr.co.morandi.backend.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.infrastructure.persistence.problem.ProblemRepository;
import kr.co.morandi.backend.domain.record.dailyrecord.model.DailyRecord;
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

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.model.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
class DailyDefenseServiceTest {

    @Autowired
    private DailyDefenseService dailyDefenseService;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyDefenseProblemRepository dailyDefenseProblemRepository;

    @Autowired
    private DailyDetailRepository dailyDetailRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        dailyDetailRepository.deleteAllInBatch();
        dailyRecordRepository.deleteAllInBatch();
        dailyDefenseProblemRepository.deleteAllInBatch();
        dailyDefenseRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("오늘의 문제 시작 테스트")
    void startDailyDefense() {
        //given
        Member member = createMember();
        LocalDate date = LocalDate.of(2021, 1, 1);

        DailyDefense dailyDefense = createDailyDefense(date);

        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);

        //when
        DailyRecord record = dailyDefenseService.startDailyDefense(now, member, 2L);

        // then
        assertThat(record).isNotNull();
        assertThat(record.getDetails())
                .extracting("problemNumber")
                .containsExactly(2L);

    }

    @Test
    @DisplayName("이미 시작한 문제 다시 시작 테스트")
    void restartExistingProblemTest() {
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
//        DailyRecord firstAttempt = dailyDefenseService.startDailyDefense(now, member, 2L);
//        DailyRecord secondAttempt = dailyDefenseService.startDailyDefense(now, member, 2L);
//        assertThat(firstAttempt.getId()).isEqualTo(secondAttempt.getId());
    }

    @Test
    @DisplayName("존재하지 않는 문제 시작 시도 테스트")
    void startNonexistentProblemTest() {
        LocalDateTime now = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        Long nonexistentProblemId = -1L; // 존재하지 않는 문제 ID
//        assertThrows(IllegalArgumentException.class, () -> dailyDefenseService.startDailyDefense(now, member, nonexistentProblemId));
    }

    @Test
    @DisplayName("날짜 불일치로 인한 문제 시작 실패 테스트")
    void startProblemOnWrongDateTest() {
        LocalDateTime wrongDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0).plusDays(1);
//        assertThrows(IllegalArgumentException.class, () -> dailyDefenseService.startDailyTest(wrongDate, member, problem.getId()));
    }

    private Member createMember() {
        return memberRepository.save(Member.create("nickname", "email", GOOGLE, "dd", "description"));
    }

    private DailyDefense createDailyDefense(LocalDate date) {
        final List<Problem> problems = createProblems();
        return dailyDefenseRepository.save(DailyDefense.create(date, "오늘의 문제 테스트", problems));
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }

}