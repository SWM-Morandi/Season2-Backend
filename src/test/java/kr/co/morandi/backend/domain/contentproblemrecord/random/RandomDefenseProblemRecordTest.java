package kr.co.morandi.backend.domain.contentproblemrecord.random;

import kr.co.morandi.backend.domain.contentrecord.random.RandomDefenseRecord;
import kr.co.morandi.backend.domain.contenttype.random.randomdefense.RandomDefense;
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
class RandomDefenseProblemRecordTest {
    @DisplayName("RandomDefenseProblemRecord를 생성한다.")
    @Test
    void create() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomDefenseRecord randomDefenseRecord = mock(RandomDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDefenseProblemRecord randomDefenseProblemRecord = RandomDefenseProblemRecord.create(member, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord).isNotNull()
                .extracting("member", "problem", "contentType", "contentRecord")
                .contains(member, problem, randomDefense, randomDefenseRecord);

    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 isSolved는 false이다")
    @Test
    void initialIsSolvedFalse() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomDefenseRecord randomDefenseRecord = mock(RandomDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDefenseProblemRecord randomDefenseProblemRecord = RandomDefenseProblemRecord.create(member, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord.getIsSolved()).isFalse();
    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 submitCount는 0이다")
    @Test
    void initialSubmitCountIsZero() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomDefenseRecord randomDefenseRecord = mock(RandomDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDefenseProblemRecord randomDefenseProblemRecord = RandomDefenseProblemRecord.create(member, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord.getSubmitCount()).isZero();
    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 solvedCode는 null이다")
    @Test
    void initialSolvedCodeIsSetToNull() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomDefenseRecord randomDefenseRecord = mock(RandomDefenseRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDefenseProblemRecord randomDefenseProblemRecord = RandomDefenseProblemRecord.create(member, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord.getSolvedCode())
                .isEqualTo(null);
    }

    private Problem createProblem() {
        return Problem.create(1L, B5, 0L);
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
}