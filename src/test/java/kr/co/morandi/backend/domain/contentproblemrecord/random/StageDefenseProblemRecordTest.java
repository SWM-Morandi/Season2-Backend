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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class StageDefenseProblemRecordTest {
    @DisplayName("스테이지 번호에 따른 스테이지 문제 기록을 만들 수 있다.")
    @Test
    void createStageDefenseProblemRecord() {
        // given
        RandomStageDefense randomStageDefense = mock(RandomStageDefense.class);
        StageDefenseRecord stageDefenseRecord = mock(StageDefenseRecord.class);
        Problem problem = Problem.create(1L, B5, 100L);
        Member member = createMember();

        // when
        StageDefenseProblemRecord stageDefenseProblemRecord = StageDefenseProblemRecord.create(1L, member, problem, stageDefenseRecord, randomStageDefense);

        // then
        assertThat(stageDefenseProblemRecord).isInstanceOf(StageDefenseProblemRecord.class)
                .extracting("stageNumber")
                .isEqualTo(1L);

    }
    private Member createMember() {
        return Member.create("user", "user" + "@gmail.com", GOOGLE, "user", "user");
    }
}