package kr.co.morandi.backend.domain.defense.randomcriteria;

import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.defense.tier.model.ProblemTier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B1;
import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.B5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class DifficultyRangeTest {

    @DisplayName("같은 난이도로 DifficultyRange를 생성할 수 있다.")
    @Test
    void startAndEndDifficultySameException() {
        // given
        ProblemTier start = B1;
        ProblemTier end = B1;

        // when
        RandomCriteria.DifficultyRange difficultyRange = RandomCriteria.DifficultyRange.of(start, end);

        // then
        assertThat(difficultyRange)
                .extracting("startDifficulty", "endDifficulty")
                .containsExactly(start, end);

    }

    @DisplayName("최소 난이도가 최대 난이도보다 큰 값으로 DifficultyRange를 생성하려고 하면 예외가 발생한다.")
    @Test
    void startDifficultyGreaterThanEndDifficultyException() {
        // given
        ProblemTier start = B1;
        ProblemTier end = B5;

        // when & then
        assertThatThrownBy(() -> RandomCriteria.DifficultyRange.of(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start difficulty must be less than or equal to end difficulty");
    }

    @DisplayName("시작 난이도나 끝 난이도가 null로 DifficultyRange를 생성하려고 하면 예외가 발생한다.")
    @Test
    void startOrEndDifficultyNullException() {
        // given
        ProblemTier start = null;
        ProblemTier end = B5;

        // when & then
        assertThatThrownBy(() -> RandomCriteria.DifficultyRange.of(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("DifficultyRange must not be null");
    }
}