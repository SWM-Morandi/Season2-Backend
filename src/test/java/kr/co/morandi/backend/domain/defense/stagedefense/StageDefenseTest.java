package kr.co.morandi.backend.domain.defense.stagedefense;

import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.defense.stagedefense.model.StageDefense;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B1;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class StageDefenseTest {
    @DisplayName("스테이지 모드를 처음 만들 때 정보가 올바르게 저장되어야 한다.")
    @Test
    void createRamdomStageDefense() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when
        StageDefense randomStageDefense = StageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");

        // then
        assertThat(randomStageDefense)
                .extracting("randomCriteria.minSolvedCount", "randomCriteria.maxSolvedCount", "timeLimit",
                        "randomCriteria.difficultyRange.startDifficulty", "randomCriteria.difficultyRange.endDifficulty",
                        "contentName")
                .containsExactly(100L, 200L, 120L, B5, B1, "브론즈 스테이지 모드");
    }
    @DisplayName("스테이지 모드를 처음 만들 때 평균 스테이지 수는 0으로 설정되어 있어야 한다.")
    @Test
    void averageStageIsZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when
        StageDefense randomStageDefense = StageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");

        // then
        assertThat(randomStageDefense.getAverageStage()).isZero();
    }
    @DisplayName("스테이지 모드를 처음 만들 때 시도한 사람 수는 0명 이어야 한다.")
    @Test
    void attemptCountIsZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when
        StageDefense randomStageDefense = StageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");

        // then
        assertThat(randomStageDefense.getAttemptCount()).isZero();
    }
    @DisplayName("스테이지 모드를 처음 만들 때 시간 제한은 0분 미만일 경우 예외가 발생한다.")
    @Test
    void timeLimitGreatherThanZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);

        // when & then
        assertThatThrownBy(() -> StageDefense.create(randomCriteria, 0L, "브론즈 스테이지 모드"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("스테이지 모드 제한 시간은 0보다 커야 합니다.");
    }
}