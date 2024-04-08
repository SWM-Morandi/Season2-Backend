package kr.co.morandi.backend.defense_management.infrastructure.adapter.problemcontent;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.defense_management.application.response.problemcontent.ProblemContent;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
class ProblemContentAdapterTest {

    private ProblemContentAdapter problemContentAdapter;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String mockServerUrl = mockWebServer.url("/")
                .toString();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockServerUrl)
                .build();

        problemContentAdapter = new ProblemContentAdapter(webClient, objectMapper);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("문제 번호 리스트를 받아서 해당 문제 번호의 문제 정보를 반환한다.")
    @Test
    void getProblemContents()  {
        // given
        List<Long> list = List.of(1000L, 1001L);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("""
                           [
                               {
                                    "baekjoonProblemId": 1000,
                                    "title": "A+B",
                                },
                               {
                                    "baekjoonProblemId": 1001,
                                    "title": "A-B",
                                }
                            ]""")
                .addHeader("Content-Type", "application/json"));


        // when
        final Map<Long, ProblemContent> result = problemContentAdapter.getProblemContents(list);

        // then
        assertThat(result.values()).hasSize(2)
                .extracting("baekjoonProblemId", "title")
                .containsExactlyInAnyOrder(
                        tuple(1000L, "A+B"),
                        tuple(1001L, "A-B")
                );


    }

    @DisplayName("존재하지 않는 문제 번호를 포함하여 요청하면 해당 문제 번호를 제외하고 반환한다.")
    @Test
    void getProblemContentsContainsInvalidBaekjoonProblemId() {
        // given
        List<Long> list = List.of(1000L, 1001L, 999L);
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("""
                           [
                               {
                                    "baekjoonProblemId": 1000,
                                    "title": "A+B",
                                },
                               {
                                    "baekjoonProblemId": 1001,
                                    "title": "A-B",
                                },
                                {
                                    "error" : "problem/999.json not exist"
                                }
                            ]""")
                .addHeader("Content-Type", "application/json"));

        // when
        final Map<Long, ProblemContent> result = problemContentAdapter.getProblemContents(list);

        // then
        assertThat(result.values()).hasSize(2)
                .extracting("baekjoonProblemId", "title")
                .containsExactlyInAnyOrder(
                        tuple(1000L, "A+B"),
                        tuple(1001L, "A-B")
                );

    }

    @DisplayName("10개 이상의 문제 번호를 요청하면 예외가 발생한다.")
    @Test
    void getProblemContentsContainsMoreThan10BaekjoonProblemId() {
        // given
        List<Long> list = List.of(1000L, 1001L, 1002L, 1003L, 1004L, 1005L, 1006L, 1007L, 1008L, 1009L, 1010L);

        // when & then
        assertThatThrownBy(() -> problemContentAdapter.getProblemContents(list))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("문제 번호는 10개 이하로 요청해주세요.");

    }
}