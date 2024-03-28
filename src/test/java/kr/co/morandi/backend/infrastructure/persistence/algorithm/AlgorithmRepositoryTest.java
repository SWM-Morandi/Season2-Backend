package kr.co.morandi.backend.infrastructure.persistence.algorithm;

import kr.co.morandi.backend.domain.algorithm.model.Algorithm;
import kr.co.morandi.backend.infrastructure.persistence.algorithm.AlgorithmRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AlgorithmRepositoryTest {

    @Autowired
    private AlgorithmRepository algorithmRepository;
    @AfterEach
    void tearDown() {
        algorithmRepository.deleteAllInBatch();
    }

    @DisplayName("알고리즘 초기 데이터가 존재하는지 확인한다.")
    @Test
    void existsByBojTagIdOrAlgorithmKey() {
        // given
        Algorithm algorithm1 = Algorithm.builder()
                .bojTagId(1)
                .algorithmKey("key1")
                .algorithmName("name1")
                .build();

        Algorithm algorithm2 = Algorithm.builder()
                .bojTagId(2)
                .algorithmKey("key2")
                .algorithmName("name2")
                .build();

        algorithmRepository.saveAll(List.of(algorithm1, algorithm2));

        // when
        boolean exists1 = algorithmRepository.existsByBojTagIdOrAlgorithmKey(1, "key1");
        boolean exists2 = algorithmRepository.existsByBojTagIdOrAlgorithmKey(2, "key2");


        // then
        assertThat(exists1).isTrue();
        assertThat(exists2).isTrue();
    }

}