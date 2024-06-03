package kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementErrorCode;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit.BaekjoonSubmitHtmlParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class BaekjoonHtmlParserTest {

    private final BaekjoonSubmitHtmlParser baekjoonHtmlParser = new BaekjoonSubmitHtmlParser();

    @DisplayName("CSRF 키를 정상적으로 파싱한다.")
    @Test
    void parseCsrfKeyInSubmitPage_validResponse() {
        // given
        String validHtml = "<html><body><input name='csrf_key' value='validCsrfKey' /></body></html>";

        // when
        String csrfKey = baekjoonHtmlParser.parseCsrfKeyInSubmitPage(validHtml);

        // then
        assertThat(csrfKey).isEqualTo("validCsrfKey");
    }

    @DisplayName("CSRF 키가 없는 경우 예외를 던진다.")
    @Test
    void parseCsrfKeyInSubmitPage_missingCsrfKey() {
        // given
        String invalidHtml = "<html><body></body></html>";

        // when & then
        assertThatThrownBy(() -> baekjoonHtmlParser.parseCsrfKeyInSubmitPage(invalidHtml))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementErrorCode.CSRF_KEY_NOT_FOUND.getMessage());
    }

    @DisplayName("null 응답인 경우 예외를 던진다.")
    @Test
    void parseCsrfKeyInSubmitPage_nullResponse() {
        // given
        String nullResponse = null;

        // when & then
        assertThatThrownBy(() -> baekjoonHtmlParser.parseCsrfKeyInSubmitPage(nullResponse))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementErrorCode.BAEKJOON_SUBMIT_PAGE_ERROR.getMessage());
    }

    @DisplayName("솔루션 아이디를 정상적으로 파싱한다.")
    @Test
    void parseSolutionIdFromHtml_validHtml() {
        // given
        String validHtml = """
                <table id="status-table">
                    <tbody>
                        <tr>
                            <td>123456</td>
                        </tr>
                    </tbody>
                </table>
                """;

        // when
        String solutionId = baekjoonHtmlParser.parseSolutionIdFromHtml(validHtml);

        // then
        assertThat(solutionId).isEqualTo("123456");
    }

    @DisplayName("솔루션 아이디가 없는 경우 예외를 던진다.")
    @Test
    void parseSolutionIdFromHtml_missingSolutionId() {
        // given
        String invalidHtml = """
                <table id="status-table">
                    <tbody>
                        <tr>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
                """;

        // when & then
        assertThatThrownBy(() -> baekjoonHtmlParser.parseSolutionIdFromHtml(invalidHtml))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementErrorCode.CANT_FIND_SOLUTION_ID.getMessage());
    }

    @DisplayName("상태 테이블이 없는 경우 예외를 던진다.")
    @Test
    void parseSolutionIdFromHtml_missingStatusTable() {
        // given
        String invalidHtml = "<html><body></body></html>";

        // when & then
        assertThatThrownBy(() -> baekjoonHtmlParser.parseSolutionIdFromHtml(invalidHtml))
                .isInstanceOf(MorandiException.class)
                .hasMessage(JudgementErrorCode.CANT_FIND_SOLUTION_ID.getMessage());
    }
}
