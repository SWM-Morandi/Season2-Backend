package kr.co.morandi.backend.defense_record.application.usecase;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_record.application.dto.DailyDefenseRankPageResponse;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Transactional
class DailyRecordRankUseCaseTest extends IntegrationTestSupport {

    @Autowired
    private DailyRecordRankUseCase dailyRecordRankUseCase;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("특정 시점 DailyRecord의 순위를 조회할 수 있다.")
    @Test
    void getDailyRecordsRankByDate() {
        // given
        LocalDate today = LocalDate.of(2021, 10, 1);
        final DailyDefense dailyDefense = createDailyDefense(today);

        final Member member1 = createMember("userA", "userA");
        final Member member2 = createMember("userB", "userB");
        final Member member3 = createMember("userC", "userC");
        final Member member4= createMember("userD", "userD");
        final Member member5 = createMember("userE", "userE");

        final DailyRecord dailyRecord1 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member1, getProblem(dailyDefense, 1L));
        final DailyRecord dailyRecord2 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member2, getProblem(dailyDefense, 2L));
        final DailyRecord dailyRecord3 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member3, getProblem(dailyDefense, 3L));
        final DailyRecord dailyRecord4 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member4, getProblem(dailyDefense, 3L));
        final DailyRecord dailyRecord5 = DailyRecord.tryDefense(LocalDateTime.of(2021, 10, 1, 0, 0), dailyDefense, member5, getProblem(dailyDefense, 3L));

        /*
         * member1: 한 문제 해결 일찍
         * member2 : 두 문제 해결
         * member3: 한 문제 해결 1보다 늦게
         *
         * -> 등수 = 2 -> 1 -> 3
         * */
        dailyRecord1.solveProblem(1L, "exampleCode", LocalDateTime.of(2021, 10, 1, 0, 15));

        dailyRecord2.solveProblem(2L, "exampleCode", LocalDateTime.of(2021, 10, 1, 0, 30));
        dailyRecord2.tryMoreProblem(getProblem(dailyDefense, 3L));
        dailyRecord2.solveProblem(3L, "exampleCode", LocalDateTime.of(2021, 10, 1, 0, 45));

        dailyRecord3.solveProblem(3L, "exampleCode", LocalDateTime.of(2021, 10, 1, 1, 0));

        dailyRecordRepository.saveAll(List.of(dailyRecord1, dailyRecord2, dailyRecord3, dailyRecord4, dailyRecord5));


        // when
        LocalDateTime requestTime = LocalDateTime.of(2021, 10, 1, 2, 0);
        final DailyDefenseRankPageResponse dailyRecordRank = dailyRecordRankUseCase.getDailyRecordRank(requestTime, 0, 5);

        // then
        assertThat(dailyRecordRank.getDailyRecords()).hasSize(5)
                .extracting("rank", "nickname", "solvedCount", "totalSolvedTime")
                .containsExactly(
                        tuple(1L, "userB", 2L, "01:15:00"),
                        tuple(2L, "userA", 1L, "00:15:00"),
                        tuple(3L, "userC", 1L, "01:00:00"),
                        //TODO 동점자 처리 로직 반영 X
                        tuple(4L, "userD", 0L, "00:00:00"),
                        tuple(5L, "userE", 0L, "00:00:00")

                );
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
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);

        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember(String nickname, String email) {
        return memberRepository.save(Member.create(nickname, email + "@gmail.com", GOOGLE, "test", "test"));
    }

}