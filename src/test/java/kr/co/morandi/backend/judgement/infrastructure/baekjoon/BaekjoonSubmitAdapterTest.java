package kr.co.morandi.backend.judgement.infrastructure.baekjoon;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementErrorCode;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit.BaekjoonSubmitHtmlParser;
import kr.co.morandi.backend.judgement.infrastructure.baekjoon.submit.BaekjoonSubmitAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class BaekjoonSubmitAdapterTest {

    private BaekjoonSubmitAdapter baekjoonSubmitAdapter;
    private ExchangeFunction exchangeFunction;
    private final BaekjoonSubmitHtmlParser baekjoonHtmlParser = new BaekjoonSubmitHtmlParser();

    @BeforeEach
    void setUp() {
        exchangeFunction = Mockito.mock(ExchangeFunction.class);
        WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .baseUrl("https://www.acmicpc.net")
                .build();

        this.baekjoonSubmitAdapter = new BaekjoonSubmitAdapter(webClient, baekjoonHtmlParser);
    }

    @DisplayName("제출을 하고 솔루션 아이디를 가져온다.")
    @Test
    void submitAndGetSolutionId() {
        // given
        // reqeust parameter
        String 백준_문제_ID = "1000";
        String 사용자_쿠키 = "cookieValue";
        Language 제출_언어 = Language.PYTHON;
        String 제출_코드_공개범위 = "open";
        String 제출_코드 = "print('Hello World')";

        // WebClient response stubbing
        String csrfKey = "stubbingCsrfKey";
        String csrfHtml = "<input name='csrf_key' value='" + csrfKey + "' />";
        String solutionIdHtml = """
                <table id="status-table">
                    <tbody>
                        <tr>
                            <td>123456</td>
                        </tr>
                    </tbody>
                </table>
                """;

        // WebClient stubbing
        when(exchangeFunction.exchange(any(ClientRequest.class)))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(csrfHtml)
                        .build()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, "/status")
                        .build()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(solutionIdHtml)
                        .build()));

        // when
        String solutionId = baekjoonSubmitAdapter.submitAndGetSolutionId(백준_문제_ID, 사용자_쿠키, 제출_언어, 제출_코드, 제출_코드_공개범위);

        // then
        assertThat(solutionId)
                .isNotNull()
                .isEqualTo("123456");
        verify(exchangeFunction, Mockito.times(3))
                .exchange(any(ClientRequest.class));

    }

    @DisplayName("제출할 때 CSRF_KEY를 가져오는데 HTML이 빈 문자열인 경우")
    @Test
    void CSRF_KEY를_가져오는데_HTML이_빈_문자열인_경우() {
        // given
        // reqeust parameter
        String 백준_문제_ID = "1000";
        String 사용자_쿠키 = "cookieValue";
        Language 제출_언어 = Language.PYTHON;
        String 제출_코드_공개범위 = "open";
        String 제출_코드 = "print('Hello World')";

        // WebClient response stubbing

        // invalid csrf key
        String csrfHtml = "";


        when(exchangeFunction.exchange(any(ClientRequest.class)))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(csrfHtml)
                        .build()));

        // when & then
        assertThatThrownBy(() -> baekjoonSubmitAdapter.submitAndGetSolutionId(백준_문제_ID, 사용자_쿠키, 제출_언어, 제출_코드, 제출_코드_공개범위))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("제출 페이지에서 CSRF 키를 찾을 수 없습니다.");

    }

    @DisplayName("제출할 때 CSRF_KEY를 가져오는데 실패한 경우")
    @Test
    void CSRF_KEY를_가져오는데_실패한_경우() {
        // given
        // reqeust parameter
        String 백준_문제_ID = "1000";
        String 사용자_쿠키 = "cookieValue";
        Language 제출_언어 = Language.PYTHON;
        String 제출_코드_공개범위 = "open";
        String 제출_코드 = "print('Hello World')";

        // WebClient response stubbing

        // invalid csrf key
        String csrfHtml = "<input name='csrf2_key' value='csrfKey' />";


        when(exchangeFunction.exchange(any(ClientRequest.class)))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(csrfHtml)
                        .build()));

        // when & then
        assertThatThrownBy(() -> baekjoonSubmitAdapter.submitAndGetSolutionId(백준_문제_ID, 사용자_쿠키, 제출_언어, 제출_코드, 제출_코드_공개범위))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining("제출 페이지에서 CSRF 키를 찾을 수 없습니다.");

    }

    @DisplayName("제출을 한 뒤 Solution Id를 정상적으로 찾지 못한 경우")
    @Test
    void 제출_뒤_Solution_Id를_정상적으로_찾지_못한_경우() {
        // given
        // reqeust parameter
        String 백준_문제_ID = "1000";
        String 사용자_쿠키 = "cookieValue";
        Language 제출_언어 = Language.PYTHON;
        String 제출_코드_공개범위 = "open";
        String 제출_코드 = "print('Hello World')";

        // WebClient response stubbing
        String csrfKey = "stubbingCsrfKey";
        String csrfHtml = "<input name='csrf_key' value='" + csrfKey + "' />";

        // invalid solution id HTML
        String solutionIdHtml = """
                <table id="sta-table">
                    <tbody>
                        <tr>
                            <td>123456</td>
                        </tr>
                    </tbody>
                </table>
                """;

        // WebClient stubbing
        when(exchangeFunction.exchange(any(ClientRequest.class)))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(csrfHtml)
                        .build()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, "/status")
                        .build()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(solutionIdHtml)
                        .build()));

        // when & then
        assertThatThrownBy(() -> baekjoonSubmitAdapter.submitAndGetSolutionId(백준_문제_ID, 사용자_쿠키, 제출_언어, 제출_코드, 제출_코드_공개범위))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining(JudgementErrorCode.CANT_FIND_SOLUTION_ID.getMessage());

    }


    @DisplayName("Redirection에서 location 헤더가 없는 경우 예외를 던진다.")
    @Test
    void handleRedirection_noLocationHeader() {
        // given
        // reqeust parameter
        String 백준_문제_ID = "1000";
        String 사용자_쿠키 = "cookieValue";
        Language 제출_언어 = Language.PYTHON;
        String 제출_코드_공개범위 = "open";
        String 제출_코드 = "print('Hello World')";

        // WebClient response stubbing
        String csrfKey = "stubbingCsrfKey";
        String csrfHtml = "<input name='csrf_key' value='" + csrfKey + "' />";

        when(exchangeFunction.exchange(any(ClientRequest.class)))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "text/html")
                        .body(csrfHtml)
                        .build()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.FOUND)
                        .header("Content-Type", "text/html")
                        .build()));


        // when & then
        assertThatThrownBy(() -> baekjoonSubmitAdapter.submitAndGetSolutionId(백준_문제_ID, 사용자_쿠키, 제출_언어, 제출_코드, 제출_코드_공개범위))
                .isInstanceOf(MorandiException.class)
                .hasMessageContaining(JudgementErrorCode.REDIRECTION_LOCATION_NOT_FOUND.getMessage());

    }



    }
