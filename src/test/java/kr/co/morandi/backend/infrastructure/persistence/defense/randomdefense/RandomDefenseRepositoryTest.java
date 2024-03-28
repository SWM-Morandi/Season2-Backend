package kr.co.morandi.backend.infrastructure.persistence.defense.randomdefense;


import kr.co.morandi.backend.domain.defense.random.model.RandomDefense;
import kr.co.morandi.backend.infrastructure.persistence.defense.random.RandomDefenseRepository;
import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static kr.co.morandi.backend.domain.defense.tier.model.ProblemTier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class RandomDefenseRepositoryTest {

    @Autowired
    private RandomDefenseRepository randomDefenseRepository;
    @AfterEach
    void tearDown() {
        randomDefenseRepository.deleteAllInBatch();
    }
    @Test
    @DisplayName("저장된 랜덤 디펜스의 정보를 조회할 수 있다.")
    void findByContentName() {
        // given
        List<RandomDefense> randomDefenses = createRandomDefense();
        randomDefenseRepository.saveAll(randomDefenses);

        // when
        RandomDefense findRandomDefense = randomDefenseRepository.findById(randomDefenses.get(0).getDefenseId()).get();

        // then
        assertThat(findRandomDefense)
                .extracting("randomCriteria.maxSolvedCount", "randomCriteria.minSolvedCount", "timeLimit", "problemCount", "randomCriteria.difficultyRange.startDifficulty", "randomCriteria.difficultyRange.endDifficulty")
                .containsExactly(200L, 100L, 1000L, 4, B5, B1);
    }
    @DisplayName("랜덤 디펜스들을 모두 조회하여 가져올 수 있다.")
    @Test
    void findAllRandomDefense(){
        // given
        List<RandomDefense> randomDefenses = createRandomDefense();
        randomDefenseRepository.saveAll(randomDefenses);

        // when
        List<RandomDefense> findRandomDefenses = randomDefenseRepository.findAll();

        // then
        assertThat(findRandomDefenses).hasSize(3)
                .extracting("randomCriteria.maxSolvedCount", "randomCriteria.minSolvedCount", "timeLimit", "problemCount", "randomCriteria.difficultyRange.startDifficulty", "randomCriteria.difficultyRange.endDifficulty")
                .containsExactlyInAnyOrder(
                        tuple(200L, 100L, 1000L, 4, B5, B1),
                        tuple(200L, 100L, 1000L, 4, S5, S1),
                        tuple(200L, 100L, 1000L, 4, G5, G1)
                );
    }
    private List<RandomDefense> createRandomDefense() {
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria.DifficultyRange silverRange = RandomCriteria.DifficultyRange.of(S5, S1);
        RandomCriteria.DifficultyRange goldRange = RandomCriteria.DifficultyRange.of(G5, G1);

        RandomDefense bronzeDefense = RandomDefense.builder()
                .timeLimit(1000L)
                .problemCount(4)
                .contentName("브론즈 랜덤 디펜스")
                .randomCriteria(RandomCriteria.of(bronzeRange,100L,200L))
                .build();

        RandomDefense silverDefense = RandomDefense.builder()
                .timeLimit(1000L)
                .problemCount(4)
                .contentName("실버 랜덤 디펜스")
                .randomCriteria(RandomCriteria.of(silverRange,100L,200L))
                .build();

        RandomDefense goldDefense = RandomDefense.builder()
                .timeLimit(1000L)
                .problemCount(4)
                .contentName("골드 랜덤 디펜스")
                .randomCriteria(RandomCriteria.of(goldRange,100L,200L))
                .build();

        return List.of(bronzeDefense, silverDefense, goldDefense);
    }
}