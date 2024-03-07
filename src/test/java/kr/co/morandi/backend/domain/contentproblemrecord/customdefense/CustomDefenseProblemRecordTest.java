package kr.co.morandi.backend.domain.contentproblemrecord.customdefense;

import kr.co.morandi.backend.domain.contentrecord.customdefense.CustomDefenseRecord;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblems;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.contenttype.customdefense.DefenseTier.GOLD;
import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.OPEN;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.S5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


@ActiveProfiles("test")
class CustomDefenseProblemRecordTest {

    @DisplayName("CustomDefenseProblemRecord를 생성할 수 있다.")
    @Test
    void create() {
        // given
        CustomDefense customDefense = createCustomDefense();
        Member member = createMember("member");
        Problem problem = customDefense.getCustomDefenseProblems().stream()
                                                .map(CustomDefenseProblems::getProblem)
                                                .findFirst()
                                                .orElse(null);

        CustomDefenseRecord customDefenseRecord = mock(CustomDefenseRecord.class);

        // when
        CustomDefenseProblemRecord customDefenseProblemRecord = CustomDefenseProblemRecord.create(member, problem, customDefenseRecord, customDefense);

        // then
        assertThat(customDefenseProblemRecord).isNotNull()
                .extracting("member", "problem", "contentType", "contentRecord")
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
                .map(CustomDefenseProblems::getProblem)
                .findFirst()
                .orElse(null);

        CustomDefenseRecord customDefenseRecord = mock(CustomDefenseRecord.class);

        // when
        CustomDefenseProblemRecord customDefenseProblemRecord = CustomDefenseProblemRecord.create(member, problem, customDefenseRecord, customDefense);

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