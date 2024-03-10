package kr.co.morandi.backend.domain.record.randomdefense;

import kr.co.morandi.backend.domain.defense.random.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.defense.random.RandomDefense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.defense.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class RandomDefenseRecordTest {
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 맞춘 문제수는 0문제 이어야 한다.")
    @Test
    void solvedCountIsZero() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getSolvedCount()).isZero();
    }
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 총 문제 수는 랜덤 디펜스 문제 개수와 같아야 한다.")
    @Test
    void problemCountIsEqual() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getProblemCount()).isEqualTo(randomDefense.getProblemCount());
    }

    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 전체 소요 시간은 0분 이어야 한다.")
    @Test
    void totalSolvedTimeIsZero() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 시점과 랜덤 디펜스 시험 날짜는 같아야 한다.")
    @Test
    void testDateIsEqualTestDate() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getTestDate()).isEqualTo(now);
    }
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 세부 문제 기록의 정답 여부는 모두 오답이어야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getContentProblemRecords())
                .extracting("isSolved")
                .containsExactly(
                false,
                        false,
                        false,
                        false
                );
    }
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 세부 문제 기록의 제출 횟수는 모두 0회여야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getContentProblemRecords())
                .extracting("submitCount")
                .containsExactly(
                    0L,
                            0L,
                            0L,
                            0L
                );
    }
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 세부 문제 기록의 정답 코드는 모두 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();

        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getContentProblemRecords())
                .extracting("solvedCode")
                .containsExactly(
                    null,
                            null,
                            null,
                            null
                );
    }
    @DisplayName("랜덤 디펜스 기록이 만들어졌을 때 세부 문제 기록의 소요 시간은 모두 0분 이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        RandomDefense randomDefense = createRandomDefense();
        List<Problem> problems = getProblemsByRandom();
        Member member = createMember("user");
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0,0,0);

        // when
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems);

        // then
        assertThat(randomDefenseRecord.getContentProblemRecords())
                .extracting("solvedTime")
                .containsExactly(
                    0L,
                            0L,
                            0L,
                            0L
                );
    }
    private RandomDefense createRandomDefense() {
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        return RandomDefense.create(randomCriteria, 4, 120L, "브론즈 랜덤 디펜스");
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
    private List<Problem> getProblemsByRandom() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        Problem problem4 = Problem.create(4L, P5, 0L);
        return List.of(problem1, problem2, problem3, problem4);
    }
}