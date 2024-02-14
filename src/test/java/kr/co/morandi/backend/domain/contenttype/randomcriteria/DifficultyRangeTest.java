package kr.co.morandi.backend.domain.contenttype.randomcriteria;

import kr.co.morandi.backend.domain.contenttype.tier.ProblemTier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
class DifficultyRangeTest {

    @DisplayName("같은 난이도로 DifficultyRange를 생성하려고 하면 예외가 발생한다.")
    @Test
    void startAndEndDifficultySameException() {
        // given
        ProblemTier start = ProblemTier.B1;
        ProblemTier end = ProblemTier.B1;

        // when & then
        assertThatThrownBy(() -> DifficultyRange.of(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start difficulty and end difficulty must not be same");

    }

    @DisplayName("최소 난이도가 최대 난이도보다 같거나 큰 값으로 DifficultyRange를 생성하려고 하면 예외가 발생한다.")
    @Test
    void startDifficultyGreaterThanEndDifficultyException() {
        // given
        ProblemTier start = ProblemTier.B1;
        ProblemTier end = ProblemTier.B5;

        // when & then
        assertThatThrownBy(() -> DifficultyRange.of(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start difficulty must be less than or equal to end difficulty");
    }

    @DisplayName("시작 난이도나 끝 난이도가 null로 DifficultyRange를 생성하려고 하면 예외가 발생한다.")
    @Test
    void startOrEndDifficultyNullException() {
        // given
        ProblemTier start = null;
        ProblemTier end = ProblemTier.B5;

        // when & then
        assertThatThrownBy(() -> DifficultyRange.of(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("DifficultyRange must not be null");
    }
}