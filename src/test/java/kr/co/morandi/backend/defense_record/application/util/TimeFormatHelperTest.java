package kr.co.morandi.backend.defense_record.application.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class TimeFormatHelperTest {

    @DisplayName("시간을 문자열로 변환한다")
    @Test
    void solvedTimeToString() {
        // given
        // 1시간 15분 37초를 초로 변환
        Long time = 3600L + (15L * 60L) + 37L;

        // when
        String result = TimeFormatHelper.solvedTimeToString(time);

        // then
        assertThat(result).isEqualTo("01:15:37");

    }

}