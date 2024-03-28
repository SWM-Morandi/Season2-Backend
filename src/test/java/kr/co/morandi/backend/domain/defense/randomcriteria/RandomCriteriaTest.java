package kr.co.morandi.backend.domain.defense.randomcriteria;

import kr.co.morandi.backend.defense_information.domain.model.defense.RandomCriteria;
import kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class RandomCriteriaTest {
    @DisplayName("출제 기준에서 최소 푼 사람 수와 최대 푼 사람 수이 0보다 작으면 예외가 발생한다.")
    @Test
    void minAndMaxSolvedCountZeroOrMoreException() {
        // given
        long minSolvedCount = -2L;
        long maxSolvedCount = -1L;
        RandomCriteria.DifficultyRange difficultyRange = RandomCriteria.DifficultyRange.of(ProblemTier.B5, ProblemTier.B1);


        // when & then
        assertThatThrownBy(() -> RandomCriteria.of(difficultyRange, minSolvedCount, maxSolvedCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Solved count must be greater than or equal to 0");
    }

    @DisplayName("최소 푼 사람 수가 최대 푼 사람 수보다 크면 예외가 발생한다.")
    @Test
    void minSolvedCountLessThanMaxSolvedCountException() {
        // given
        long minSolvedCount = 100L;
        long maxSolvedCount = 50L;
        RandomCriteria.DifficultyRange difficultyRange = RandomCriteria.DifficultyRange.of(ProblemTier.B5, ProblemTier.B1);

        // when & then
        assertThatThrownBy(() -> RandomCriteria.of(difficultyRange, minSolvedCount, maxSolvedCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Min solved count must be less than or equal to max solved count");

    }

    @DisplayName("푼 사람 수의 범위가 같으면 예외가 발생한다.")
    @Test
    void minAndMaxSolvedCountSameException() {
        // given
        long minSolvedCount = 100L;
        long maxSolvedCount = 100L;
        RandomCriteria.DifficultyRange difficultyRange = RandomCriteria.DifficultyRange.of(ProblemTier.B5, ProblemTier.B1);

        // when & then
        assertThatThrownBy(() -> RandomCriteria.of(difficultyRange, minSolvedCount, maxSolvedCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Min solved count must be less than or equal to max solved count");
    }

}