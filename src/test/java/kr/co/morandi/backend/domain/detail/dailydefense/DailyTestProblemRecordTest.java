package kr.co.morandi.backend.domain.detail.DailyDefense;

import kr.co.morandi.backend.domain.record.DailyDefense.DailyDefenseRecord;
import kr.co.morandi.backend.domain.defense.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.defense.dailydefense.DailyDefenseProblem;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static kr.co.morandi.backend.domain.problem.ProblemStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class DailyDefenseProblemRecordTest {
    @DisplayName("DailyDefenseProblemRecord를 만들 수 있다.")
    @Test
    void create() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyDefenseRecord DailyDefenseRecord = mock(DailyDefenseRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblemsList().stream()
                                    .map(DailyDefenseProblem::getProblem)
                                    .findFirst()
                                    .orElse(null);
        Member member = createMember();

        // when
        DailyDefenseProblemRecord DailyDefenseProblemRecord = DailyDefenseProblemRecord.create(member, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord).isNotNull()
                .extracting("member", "problem", "contentType", "contentRecord")
                .contains(member, problem, DailyDefense, DailyDefenseRecord);
    }
    @DisplayName("DailyDefenseProblemRecord가 생성되면 isSolved는 false이다")
    @Test
    void initialIsSolvedFalse() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyDefenseRecord DailyDefenseRecord = mock(DailyDefenseRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblemsList().stream()
                                        .map(DailyDefenseProblem::getProblem)
                                        .findFirst()
                                        .orElse(null);
        Member member = createMember();

        // when
        DailyDefenseProblemRecord DailyDefenseProblemRecord = DailyDefenseProblemRecord.create(member, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord.getIsSolved()).isFalse();
    }
    @DisplayName("DailyDefenseProblemRecord가 생성되면 submitCount는 0이다")
    @Test
    void initialSubmitCountIsZero() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyDefenseRecord DailyDefenseRecord = mock(DailyDefenseRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblemsList().stream()
                                        .map(DailyDefenseProblem::getProblem)
                                        .findFirst()
                                        .orElse(null);
        Member member = createMember();

        // when
        DailyDefenseProblemRecord DailyDefenseProblemRecord = DailyDefenseProblemRecord.create(member, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord.getSubmitCount()).isZero();
    }
    @DisplayName("DailyDefenseProblemRecord가 생성되면 solvedCode는 null이다")
    @Test
    void initialSolvedCodeIsSetToNull() {
        // given
        DailyDefense DailyDefense = createDailyDefense();
        DailyDefenseRecord DailyDefenseRecord = mock(DailyDefenseRecord.class);
        Problem problem = DailyDefense.getDailyDefenseProblemsList().stream()
                                        .map(DailyDefenseProblem::getProblem)
                                        .findFirst()
                                        .orElse(null);
        Member member = createMember();

        // when
        DailyDefenseProblemRecord DailyDefenseProblemRecord = DailyDefenseProblemRecord.create(member, problem, DailyDefenseRecord, DailyDefense);

        // then
        assertThat(DailyDefenseProblemRecord.getSolvedCode())
                .isEqualTo(null);
    }

    private DailyDefense createDailyDefense() {
        LocalDateTime createDate = LocalDateTime.of(2023, 3, 5, 0, 0);
        return DailyDefense.create(createDate, "3월 5일 문제", createProblem());
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