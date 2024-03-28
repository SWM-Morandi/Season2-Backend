package kr.co.morandi.backend.infrastructure.adapter.defense.record.dailyrecord;

import kr.co.morandi.backend.defense_record.application.outputport.dailyrecord.DailyRecordPort;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DailyRecordAdapterTest {

    @Autowired
    private DailyRecordPort dailyRecordPort;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        dailyRecordRepository.deleteAll();
        dailyDefenseRepository.deleteAll();
        problemRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("오늘 날짜에 해당하는 DailyRecord가 존재할 때 찾아올 수 있다.")
    @Test
    void findDailyRecordByMemberAndDate() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();
        tryDailyDefense(today, member);

        // when
        Optional<DailyRecord> foundDailyRecord = dailyRecordPort.findDailyRecord(member, today.toLocalDate());

        // then
        assertThat(foundDailyRecord).isPresent()
                .get()
                .extracting("testDate", "problemCount")
                .contains(today, 1);

    }

    @DisplayName("오늘 날짜에 해당하는 DailyRecord가 없을 때 null을 반한한다.")
    @Test
    void nullWhenDailyRecordNotExists() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();

        // when
        Optional<DailyRecord> foundDailyRecord = dailyRecordPort.findDailyRecord(member, today.toLocalDate());

        // then
        assertThat(foundDailyRecord).isNotPresent();

    }

    private void tryDailyDefense(LocalDateTime today, Member member) {
        final DailyDefense dailyDefense = createDailyDefense(today.toLocalDate());
        dailyDefenseRepository.save(dailyDefense);

        DailyRecord dailyRecord = DailyRecord.tryDefense(today, dailyDefense, member, getProblem(dailyDefense, 2L));
        dailyRecordRepository.save(dailyRecord);
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
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap);
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