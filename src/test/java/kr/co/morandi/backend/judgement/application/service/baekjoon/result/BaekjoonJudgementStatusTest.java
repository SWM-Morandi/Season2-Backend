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

}