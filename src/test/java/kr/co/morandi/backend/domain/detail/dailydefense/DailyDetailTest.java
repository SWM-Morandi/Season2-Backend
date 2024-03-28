package kr.co.morandi.backend.domain.detail.dailydefense;

import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyDetail;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.B5;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static kr.co.morandi.backend.problem_information.domain.model.problem.ProblemStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class DailyDetailTest {
    @DisplayName("DailyDefenseProblemRecord를 만들 수 있다.")
    @Test
    void create() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyRecord DailyDefenseRecord = mock(DailyRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblems().stream()
                                    .map(DailyDefenseProblem::getProblem)
                                    .findFirst()
                                    .orElse(null);
        Member member = createMember();

        // when
        DailyDetail DailyDefenseProblemRecord = DailyDetail.create(member, 1L, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord).isNotNull()
                .extracting("member", "problem", "defense", "record")
                .contains(member, problem, DailyDefense, DailyDefenseRecord);
    }
    @DisplayName("DailyDefenseProblemRecord가 생성되면 isSolved는 false이다")
    @Test
    void initialIsSolvedFalse() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyRecord DailyDefenseRecord = mock(DailyRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblems().stream()
                .map(DailyDefenseProblem::getProblem)
                .findFirst()
                .orElse(null);
        Member member = createMember();

        // when
        DailyDetail DailyDefenseProblemRecord = DailyDetail.create(member, 1L, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord.getIsSolved()).isFalse();
    }
    @DisplayName("DailyDefenseProblemRecord가 생성되면 submitCount는 0이다")
    @Test
    void initialSubmitCountIsZero() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyRecord DailyDefenseRecord = mock(DailyRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblems().stream()
                .map(DailyDefenseProblem::getProblem)
                .findFirst()
                .orElse(null);
        Member member = createMember();

        // when
        DailyDetail DailyDefenseProblemRecord = DailyDetail.create(member, 1L, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord.getSubmitCount()).isZero();
    }
    @DisplayName("DailyDefenseProblemRecord가 생성되면 solvedCode는 null이다")
    @Test
    void initialSolvedCodeIsSetToNull() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyRecord DailyDefenseRecord = mock(DailyRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblems().stream()
                .map(DailyDefenseProblem::getProblem)
                .findFirst()
                .orElse(null);
        Member member = createMember();

        // when
        DailyDetail DailyDefenseProblemRecord = DailyDetail.create(member, 1L, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord.getSolvedCode())
                .isEqualTo(null);
    }

    private DailyDefense createDailyDefense() {
        LocalDate createdDate = LocalDate.of(2024, 3, 1);
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblem().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return DailyDefense.create(createdDate, "오늘의 문제 테스트", problemMap);
    }
    private List<Problem> createProblem() {
        return List.of(
                Problem.builder()
                        .baekjoonProblemId(1L)
                        .problemTier(B5)
                        .problemStatus(ACTIVE)
                        .solvedCount(0L)
                        .build(),
                Problem.builder()
                        .baekjoonProblemId(2L)
                        .problemTier(B5)
                        .problemStatus(ACTIVE)
                        .solvedCount(0L)
                        .build(),
                Problem.builder()
                        .baekjoonProblemId(3L)
                        .problemTier(B5)
                        .problemStatus(ACTIVE)
                        .solvedCount(0L)
                        .build()
        );
    }
    private Member createMember() {
        return Member.builder()
                .email("user" + "@gmail.com")
                .socialType(GOOGLE)
                .nickname("nickname")
                .description("description")
                .build();
    }
}