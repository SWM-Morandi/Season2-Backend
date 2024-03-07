package kr.co.morandi.backend.domain.contentrecord.customdefense;

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

@ActiveProfiles("test")
class CustomDefenseRecordTest {
    @DisplayName("커스텀 디펜스 기록이 만들어 졌을 때 시험 날짜는 시작한 시점과 같아야 한다.")
    @Test
    void testDateIsEqualNow() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);

        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getTestDate()).isEqualTo(startTime);
    }
    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 맞춘 문제 수는 0으로 설정되어야 한다.")
    @Test
    void solvedCountIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);

        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getSolvedCount()).isZero();
    }
    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 총 문제 수 기록은 커스텀 디펜스 문제 수와 같아야 한다.")
    @Test
    void problemCountIsEqual() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);

        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getProblemCount()).isEqualTo(customDefense.getProblemCount());
    }
    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 초기 전체 소요 시간은 0분 이어야 한다.")
    @Test
    void totalSolvedTimeIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);

        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 세부 문제 기록의 정답 여부는 모두 오답이어야 한다.")
    @Test
    void isSolvedFalse() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);

        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getContentProblemRecords())
                .extracting("isSolved")
                .containsExactlyInAnyOrder(
                        false,
                                false
                );
    }

    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 세부 문제 기록의 문제 기록의 소요 시간은 0분이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // then
        assertThat(customDefenseRecord.getContentProblemRecords())
                .extracting("solvedTime")
                .containsExactlyInAnyOrder(
                        0L,
                                0L
                );
    }

    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 세부 문제 기록의 제출 횟수는 0회 이어야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // when & then
        assertThat(customDefenseRecord.getContentProblemRecords())
                .extracting("submitCount")
                .containsExactlyInAnyOrder(
                        0L,
                                0L
                );
    }

    @DisplayName("커스텀 디펜스 기록이 만들어졌을 때 세부 문제 기록의 문제 정답 코드는 null 값 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        CustomDefense customDefense = createCustomDefense();
        List<Problem> problems = getCustomDefenseProblems(customDefense);
        Member member = createMember("user");
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, startTime, problems);

        // when & then
        assertThat(customDefenseRecord.getContentProblemRecords())
                .extracting("solvedCode")
                .containsExactlyInAnyOrder(
                        null,
                                null
                );

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
    private List<Problem> getCustomDefenseProblems(CustomDefense customDefense) {
        List<CustomDefenseProblems> customDefenseProblems = customDefense.getCustomDefenseProblems();

        return customDefenseProblems.stream()
                .map(CustomDefenseProblems::getProblem)
                .toList();
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
}