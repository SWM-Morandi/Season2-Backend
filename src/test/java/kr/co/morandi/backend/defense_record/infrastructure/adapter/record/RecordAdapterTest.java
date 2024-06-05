package kr.co.morandi.backend.defense_record.infrastructure.adapter.record;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class RecordAdapterTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private RecordAdapter recordAdapter;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @DisplayName("RecordId로 Record를 찾아올 수 있다. (Fetch Join)")
    @Test
    void findRecordByIdFetchDetails() {
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();
        final DailyRecord dailyRecord = tryDailyDefense(today, member);

        // when
        final Optional<Record<? extends Detail>> record = recordAdapter.findRecordByIdFetchDetails(dailyRecord.getRecordId());

        // then
        assertThat(record).isPresent()
                .get()
                .extracting("recordId", "defense.contentName", "details")
                .contains(dailyRecord.getRecordId(), "오늘의 문제 테스트", dailyRecord.getDetails());
    }

    @DisplayName("RecordId로 Record를 찾아올 수 있다.")
    @Test
    void findRecordById() {
        // given
        LocalDateTime today = LocalDateTime.of(2021, 10, 1, 0, 0);

        final Member member = createMember();
        final DailyRecord dailyRecord = tryDailyDefense(today, member);

        // when
        final Optional<Record<?>> record = recordAdapter.findRecordById(dailyRecord.getRecordId());

        // then
        assertThat(record).isPresent()
                .get()
                .extracting("recordId", "defense.contentName")
                .contains(dailyRecord.getRecordId(), "오늘의 문제 테스트");

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