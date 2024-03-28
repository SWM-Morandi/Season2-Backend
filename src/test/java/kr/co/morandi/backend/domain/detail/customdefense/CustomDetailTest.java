package kr.co.morandi.backend.domain.detail.customdefense;

import kr.co.morandi.backend.domain.defense.customdefense.model.CustomDefense;
import kr.co.morandi.backend.domain.defense.customdefense.model.CustomDefenseProblem;
import kr.co.morandi.backend.domain.detail.customdefense.model.CustomDetail;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.problem.model.Problem;
import kr.co.morandi.backend.domain.record.customdefenserecord.model.CustomRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.customdefense.model.DefenseTier.GOLD;
import static kr.co.morandi.backend.domain.defense.customdefense.model.Visibility.OPEN;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B5;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.S5;
import static kr.co.morandi.backend.domain.member.model.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


@ActiveProfiles("test")
class CustomDetailTest {

    @DisplayName("CustomDefenseProblemRecord를 생성할 수 있다.")
    @Test
    void create() {
        // given
        CustomDefense customDefense = createCustomDefense();
        Member member = createMember("member");
        Problem problem = customDefense.getCustomDefenseProblems().stream()
                                                .map(CustomDefenseProblem::getProblem)
                                                .findFirst()
                                                .orElse(null);

        CustomRecord customDefenseRecord = mock(CustomRecord.class);

        // when
        CustomDetail customDefenseProblemRecord = CustomDetail.create(member, 1L, problem, customDefenseRecord, customDefense);

        // then
        assertThat(customDefenseProblemRecord).isNotNull()
                .extracting("member", "problem", "defense", "record")
                .containsExactly(
                        member, problem, customDefense, customDefenseRecord
                );
    }
    @DisplayName("CustomDefenseProblemRecord가 생성됐을 때 solvedTime은 0이다.")
    @Test
    void initialSolvedTimeIsOne() {
        // given
        CustomDefense customDefense = createCustomDefense();
        Member member = createMember("member");
        Problem problem = customDefense.getCustomDefenseProblems().stream()
                .map(CustomDefenseProblem::getProblem)
                .findFirst()
                .orElse(null);

        CustomRecord customDefenseRecord = mock(CustomRecord.class);

        // when
        CustomDetail customDefenseProblemRecord = CustomDetail.create(member, 1L, problem, customDefenseRecord, customDefense);

        // then
        assertThat(customDefenseProblemRecord.getSolvedTime())
                .isEqualTo(0L);
    }
    private CustomDefense createCustomDefense() {
        Member member = createMember("author");
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        List<Problem> problems = List.of(problem1, problem2);

        LocalDateTime now = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        return CustomDefense.create(problems, member, "custom_defense",
                "custom_defense", OPEN, GOLD, 60L, now);
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
}