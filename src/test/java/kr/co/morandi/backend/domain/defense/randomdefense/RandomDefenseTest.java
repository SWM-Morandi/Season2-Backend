package kr.co.morandi.backend.domain.defense.randomdefense;

import kr.co.morandi.backend.domain.defense.random.model.RandomDefense;
import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B1;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class RandomDefenseTest {
    @DisplayName("랜덤 디펜스 응시 시 끝나는 시간을 계산하면 시작 시간에 제한 시간을 더한 시간이어야 한다.")
    @Test
    void getEndTime() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomDefense randomDefense = RandomDefense.create(randomCriteria, 4, 120L, "브론즈 랜덤 디펜스");
        LocalDateTime startTime = LocalDateTime.of(2021, 1, 1, 0, 0);

        // when
        final LocalDateTime endTime = randomDefense.getEndTime(startTime);

        // then
        assertThat(endTime)
                .isEqualTo(startTime.plusMinutes(120L));
    }

    @DisplayName("랜덤 디펜스를 생성할 때 등록한 정보가 올바르게 저장되어야 한다.")
    @Test
    void createRandomDefense() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when
        RandomDefense randomDefense = RandomDefense.create(randomCriteria, 4, 120L, "브론즈 랜덤 디펜스");

        // then
        assertThat(randomDefense)
                .extracting("randomCriteria.DifficultyRange.startDifficulty", "randomCriteria.DifficultyRange.endDifficulty",
                        "problemCount", "timeLimit", "contentName", "RandomCriteria.minSolvedCount", "RandomCriteria.maxSolvedCount")
                .containsExactly(B5, B1, 4, 120L, "브론즈 랜덤 디펜스", 100L, 200L);
    }

    @DisplayName("랜덤 디펜스를 생성할 때 시간 제한이 0분 이하로 설정되면 예외가 발생한다.")
    @Test
    void timeLimitGreatherThanZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when & then
        assertThatThrownBy(() -> RandomDefense.create(randomCriteria, 4, 0L, "브론즈 랜덤 디펜스"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랜덤 디펜스 제한 시간은 0보다 커야 합니다.");
    }
    @DisplayName("랜덤 디펜스를 생성할 때 문제 개수가 0개 이하로 설정되면 예외가 발생한다.")
    @Test
    void problemCountGreatherThanZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when & then
        assertThatThrownBy(() -> RandomDefense.create(randomCriteria, 0, 120L, "브론즈 랜덤 디펜스"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("랜덤 디펜스 문제 수는 1문제 이상 이어야 합니다.");
    }
}