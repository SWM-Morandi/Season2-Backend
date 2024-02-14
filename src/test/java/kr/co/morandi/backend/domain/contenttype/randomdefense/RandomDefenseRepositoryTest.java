package kr.co.morandi.backend.domain.contenttype.randomdefense;


import kr.co.morandi.backend.domain.contenttype.randomcriteria.DifficultyRange;
import kr.co.morandi.backend.domain.contenttype.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.contenttype.tier.ProblemTier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
        RandomDefense findRandomDefense = randomDefenseRepository.findById(randomDefenses.get(0).getContentTypeId()).get();

        // then
        assertThat(findRandomDefense)
                .extracting("randomCriteria.maxSolvedCount", "randomCriteria.minSolvedCount", "timeLimit", "problemCount", "randomCriteria.difficultyRange.startDifficulty", "randomCriteria.difficultyRange.endDifficulty")
                .containsExactly(200L, 100L, 1000L, 4L, ProblemTier.B5, ProblemTier.B1);
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
                        tuple(200L, 100L, 1000L, 4L, ProblemTier.B5, ProblemTier.B1),
                        tuple(200L, 100L, 1000L, 4L, ProblemTier.S5, ProblemTier.S1),
                        tuple(200L, 100L, 1000L, 4L, ProblemTier.G5, ProblemTier.G1)
                );
    }

    private List<RandomDefense> createRandomDefense() {
        DifficultyRange bronzeRange = DifficultyRange.of(ProblemTier.B5, ProblemTier.B1);
        DifficultyRange silverRange = DifficultyRange.of(ProblemTier.S5, ProblemTier.S1);
        DifficultyRange goldRange = DifficultyRange.of(ProblemTier.G5, ProblemTier.G1);

        RandomDefense bronzeDefense = RandomDefense.builder()
                .timeLimit(1000L)
                .problemCount(4L)
                .contentName("브론즈 랜덤 디펜스")
                .randomCriteria(RandomCriteria.of(bronzeRange,100L,200L))
                .build();

        RandomDefense silverDefense = RandomDefense.builder()
                .timeLimit(1000L)
                .problemCount(4L)
                .contentName("실버 랜덤 디펜스")
                .randomCriteria(RandomCriteria.of(silverRange,100L,200L))
                .build();

        RandomDefense goldDefense = RandomDefense.builder()
                .timeLimit(1000L)
                .problemCount(4L)
                .contentName("골드 랜덤 디펜스")
                .randomCriteria(RandomCriteria.of(goldRange,100L,200L))
                .build();

        return List.of(bronzeDefense, silverDefense, goldDefense);
    }
}