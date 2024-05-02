package kr.co.morandi.backend.problem_information.infrastructure.config;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.problem_information.domain.model.algorithm.Algorithm;
import kr.co.morandi.backend.problem_information.infrastructure.initializer.AlgorithmInitializer;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.algorithm.AlgorithmRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class AlgorithmInitializerTest extends IntegrationTestSupport {
    @Autowired
    private AlgorithmInitializer algorithmInitializer;

    @Autowired
    private AlgorithmRepository algorithmRepository;
    @AfterEach
    void tearDown() {
        algorithmRepository.deleteAllInBatch();
    }
    @DisplayName("알고리즘 시드 데이터가 초기화가 정상적으로 이루어진다.")
    @Test
    void whenDatabaseIsEmpty_thenInitializesDataSuccessfully() throws IOException {
        // when
        algorithmInitializer.init();

        // then
        List<Algorithm> allAlgorithms = algorithmRepository.findAll();

        assertThat(allAlgorithms).hasSizeGreaterThan(0)
                .extracting("bojTagId", "algorithmKey", "algorithmName")
                    .containsAnyOf(
                            tuple(1, "2_sat", "2-sat"),
                            tuple(218, "floor_sum", "유리 등차수열의 내림 합")
                    );


    }
    @DisplayName("데이터베이스에 데이터가 이미 초기화되어 있을 때 중복 초기화가 방지된다.")
    @Test
    void whenDataAlreadyExists_thenPreventsDuplicateInitialization() throws IOException {
        // given
        algorithmInitializer.init();
        long initialCount = algorithmRepository.count();

        // when
        algorithmInitializer.init();

        // then
        assertThat(algorithmRepository.count()).isEqualTo(initialCount);
    }
}