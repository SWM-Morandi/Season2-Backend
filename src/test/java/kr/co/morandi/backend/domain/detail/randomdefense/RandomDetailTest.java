package kr.co.morandi.backend.domain.detail.randomdefense;

import kr.co.morandi.backend.defense_information.domain.model.randomdefense.model.RandomDefense;
import kr.co.morandi.backend.defense_record.domain.model.randomdefense_record.RandomDetail;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.randomdefense_record.RandomRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.B5;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class RandomDetailTest {
    @DisplayName("RandomDefenseProblemRecord를 생성한다.")
    @Test
    void create() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomRecord randomDefenseRecord = mock(RandomRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDetail randomDefenseProblemRecord = RandomDetail.create(member, 1L, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord).isNotNull()
                .extracting("member", "problem", "defense", "record")
                .contains(member, problem, randomDefense, randomDefenseRecord);

    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 solvedTime은 0이다")
    @Test
    void initialSolvedTimeIsZero() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomRecord randomDefenseRecord = mock(RandomRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDetail randomDefenseProblemRecord = RandomDetail.create(member, 1L, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord.getSolvedTime()).isZero();
    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 isSolved는 false이다")
    @Test
    void initialIsSolvedFalse() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomRecord randomDefenseRecord = mock(RandomRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDetail randomDefenseProblemRecord = RandomDetail.create(member,1L, problem, randomDefenseRecord, randomDefense);

        // then
        assertThat(randomDefenseProblemRecord.getIsSolved()).isFalse();
    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 submitCount는 0이다")
    @Test
    void initialSubmitCountIsZero() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomRecord randomDefenseRecord = mock(RandomRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDetail randomDefenseProblemRecord = RandomDetail.create(member, 1L,  problem, randomDefenseRecord, randomDefense);
        // then
        assertThat(randomDefenseProblemRecord.getSubmitCount()).isZero();
    }
    @DisplayName("RandomDefenseProblemRecord가 생성되면 solvedCode는 null이다")
    @Test
    void initialSolvedCodeIsSetToNull() {
        // given
        RandomDefense randomDefense = mock(RandomDefense.class);
        RandomRecord randomDefenseRecord = mock(RandomRecord.class);
        Problem problem = createProblem();
        Member member = createMember("test");

        // when
        RandomDetail randomDefenseProblemRecord = RandomDetail.create(member, 1L, problem, randomDefenseRecord, randomDefense);

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