package kr.co.morandi.backend.judgement.application.service.baekjoon.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonResultType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class BaekjoonJudgementStatusTest extends IntegrationTestSupport {

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("컴파일 에러 테스트")
    @Test
    void testDeserializationCompileError() throws JsonProcessingException {
        String json = "{\"result\":11,\"solution_id\":79195594}";

        BaekjoonJudgementStatus result = objectMapper.readValue(json, BaekjoonJudgementStatus.class);

        assertEquals(BaekjoonResultType.COMPILE_ERROR, result.getResult());
        assertNull(result.getProgress());
        assertNull(result.getMemory());
        assertNull(result.getTime());
        assertNull(result.getSubtaskScore());
        assertNull(result.getPartialScore());
        assertNull(result.getAc());
        assertNull(result.getTot());
        assertNull(result.getFeedback());
        assertNull(result.getRteReason());
        assertNull(result.getRemain());
    }

    @DisplayName("런타임 에러 테스트")
    @Test
    void testDeserializationRuntimeError() throws JsonProcessingException {
        String json = "{\"result\":10,\"solution_id\":79195594}";

        BaekjoonJudgementStatus result = objectMapper.readValue(json, BaekjoonJudgementStatus.class);

        assertEquals(BaekjoonResultType.RUNTIME_ERROR, result.getResult());
        assertNull(result.getProgress());
        assertNull(result.getMemory());
        assertNull(result.getTime());
        assertNull(result.getSubtaskScore());
        assertNull(result.getPartialScore());
        assertNull(result.getAc());
        assertNull(result.getTot());
        assertNull(result.getFeedback());
        assertNull(result.getRteReason());
        assertNull(result.getRemain());
    }

    @DisplayName("프로그레스 97% 테스트")
    @Test
    void testDeserializationProgress97() throws JsonProcessingException {
        String json = "{\"progress\":97,\"result\":3,\"solution_id\":79195594}";

        BaekjoonJudgementStatus result = objectMapper.readValue(json, BaekjoonJudgementStatus.class);

        assertEquals(BaekjoonResultType.PROGRESS, result.getResult());
        assertEquals(97, result.getProgress());
        assertNull(result.getMemory());
        assertNull(result.getTime());
        assertNull(result.getSubtaskScore());
        assertNull(result.getPartialScore());
        assertNull(result.getAc());
        assertNull(result.getTot());
        assertNull(result.getFeedback());
        assertNull(result.getRteReason());
        assertNull(result.getRemain());
    }

    @DisplayName("정답입니다 테스트")
    @Test
    void testDeserializationFinalResult() throws JsonProcessingException {
        String json = "{\"memory\":739436,\"result\":4,\"solution_id\":79195594,\"time\":3944}";

        BaekjoonJudgementStatus result = objectMapper.readValue(json, BaekjoonJudgementStatus.class);

        assertEquals(BaekjoonResultType.CORRECT, result.getResult());
        assertEquals(739436, result.getMemory());
        assertEquals(3944, result.getTime());
        assertNull(result.getProgress());
        assertNull(result.getSubtaskScore());
        assertNull(result.getPartialScore());
        assertNull(result.getAc());
        assertNull(result.getTot());
        assertNull(result.getFeedback());
        assertNull(result.getRteReason());
        assertNull(result.getRemain());
    }

    @DisplayName("결과가 WRONG_ANSWER일 때 isRejected가 true를 반환해야 한다")
    @Test
    void givenWrongAnswer_whenCheckingIsRejected_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.WRONG_ANSWER)
                .build();

        // when
        boolean isRejected = judgementStatus.isRejected();

        // then
        assertTrue(isRejected);
    }

    @DisplayName("결과가 RUNTIME_ERROR일 때 isRejected가 true를 반환해야 한다")
    @Test
    void givenRuntimeError_whenCheckingIsRejected_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.RUNTIME_ERROR)
                .build();

        // when
        boolean isRejected = judgementStatus.isRejected();

        // then
        assertTrue(isRejected);
    }

    @DisplayName("결과가 COMPILE_ERROR일 때 isRejected가 true를 반환해야 한다")
    @Test
    void givenCompileError_whenCheckingIsRejected_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.COMPILE_ERROR)
                .build();

        // when
        boolean isRejected = judgementStatus.isRejected();

        // then
        assertTrue(isRejected);
    }

    @DisplayName("결과가 TIME_LIMIT_EXCEEDED일 때 isRejected가 true를 반환해야 한다")
    @Test
    void givenTimeLimitExceeded_whenCheckingIsRejected_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.TIME_LIMIT_EXCEEDED)
                .build();

        // when
        boolean isRejected = judgementStatus.isRejected();

        // then
        assertTrue(isRejected);
    }

    @DisplayName("결과가 OTHER일 때 isRejected가 true를 반환해야 한다")
    @Test
    void givenOther_whenCheckingIsRejected_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.OTHER)
                .build();

        // when
        boolean isRejected = judgementStatus.isRejected();

        // then
        assertTrue(isRejected);
    }

    @DisplayName("결과가 CORRECT일 때 isRejected가 false를 반환해야 한다")
    @Test
    void givenCorrect_whenCheckingIsRejected_thenShouldReturnFalse() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.CORRECT)
                .build();

        // when
        boolean isRejected = judgementStatus.isRejected();

        // then
        assertFalse(isRejected);
    }

    @DisplayName("결과가 CORRECT일 때 isFinalResult가 true를 반환해야 한다")
    @Test
    void givenCorrect_whenCheckingIsFinalResult_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.CORRECT)
                .build();

        // when
        boolean isFinalResult = judgementStatus.isFinalResult();

        // then
        assertTrue(isFinalResult);
    }

    @DisplayName("결과가 WRONG_ANSWER일 때 isFinalResult가 true를 반환해야 한다")
    @Test
    void givenWrongAnswer_whenCheckingIsFinalResult_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.WRONG_ANSWER)
                .build();

        // when
        boolean isFinalResult = judgementStatus.isFinalResult();

        // then
        assertTrue(isFinalResult);
    }

    @DisplayName("결과가 RUNTIME_ERROR일 때 isFinalResult가 true를 반환해야 한다")
    @Test
    void givenRuntimeError_whenCheckingIsFinalResult_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.RUNTIME_ERROR)
                .build();

        // when
        boolean isFinalResult = judgementStatus.isFinalResult();

        // then
        assertTrue(isFinalResult);
    }

    @DisplayName("결과가 COMPILE_ERROR일 때 isFinalResult가 true를 반환해야 한다")
    @Test
    void givenCompileError_whenCheckingIsFinalResult_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.COMPILE_ERROR)
                .build();

        // when
        boolean isFinalResult = judgementStatus.isFinalResult();

        // then
        assertTrue(isFinalResult);
    }

    @DisplayName("결과가 TIME_LIMIT_EXCEEDED일 때 isFinalResult가 true를 반환해야 한다")
    @Test
    void givenTimeLimitExceeded_whenCheckingIsFinalResult_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.TIME_LIMIT_EXCEEDED)
                .build();

        // when
        boolean isFinalResult = judgementStatus.isFinalResult();

        // then
        assertTrue(isFinalResult);
    }

    @DisplayName("결과가 OTHER일 때 isFinalResult가 true를 반환해야 한다")
    @Test
    void givenOther_whenCheckingIsFinalResult_thenShouldReturnTrue() {
        // given
        BaekjoonJudgementStatus judgementStatus = BaekjoonJudgementStatus.builder()
                .result(BaekjoonResultType.OTHER)
                .build();

        // when
        boolean isFinalResult = judgementStatus.isFinalResult();

        // then
        assertTrue(isFinalResult);
    }

}