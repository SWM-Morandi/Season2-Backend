package kr.co.morandi.backend.domain.contentproblemrecord.random;

import kr.co.morandi.backend.domain.contentrecord.random.StageDefenseRecord;
import kr.co.morandi.backend.domain.contenttype.random.randomstagedefense.RandomStageDefense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static kr.co.morandi.backend.domain.problem.ProblemStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class StageDefenseProblemRecordTest {
    @DisplayName("스테이지 문제 기록이 생성되면 초기 정답 시간은 0이다.")
    @Test
    void initialSolvedTimeIsZero() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord.getSolvedTime()).isEqualTo(0L);

    }
    @DisplayName("원하는 스테이지 번호에 따른 스테이지 문제 기록을 만들 수 있다.")
    @Test
    void createStageDefenseProblemRecordWithStageNumber() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord.getStageNumber()).isEqualTo(1L);

    }
    @DisplayName("스테이지 문제 기록이 생성되면 초기 정답 여부는 false이다.")
    @Test
    void initialIsSolvedIsFalse() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord)
                .extracting("isSolved")
                .isEqualTo(false);

    }
    @DisplayName("스테이지 문제 기록이 생성되면 submitCount는 0이다.")
    @Test
    void initialSubmitCountIsZero() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord)
                .extracting("submitCount")
                .isEqualTo(0L);

    }
    @DisplayName("스테이지 문제 기록이 생성되면 초기 정답 코드는 null이다.")
    @Test
    void initialSolvedCodeIsNull() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord)
                .extracting("solvedCode")
                .isEqualTo(null);

    }
    @DisplayName("스테이지 문제 기록을 생성할 수 있다.")
    @Test
    void createStageDefenseProblemRecord() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord)
                .extracting("member", "problem", "contentRecord", "contentType")
                .contains(member, problem, stageDefenseRecord, randomStageDefense);
    }
    private Member createMember() {
        return Member.builder()
                .email("user" + "@gmail.com")
                .socialType(GOOGLE)
                .nickname("nickname")
                .description("description")
                .build();
    }
    private Problem createProblem() {
        return Problem.builder()
                .baekjoonProblemId(1L)
                .problemTier(B5)
                .problemStatus(ACTIVE)
                .solvedCount(0L)
                .build();
    }
}