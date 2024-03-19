package kr.co.morandi.backend.domain.defense.random.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static kr.co.morandi.backend.domain.defense.DefenseType.RANDOM;

@Entity
@DiscriminatorValue("RandomDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomDefense extends Defense {

    @Embedded
    private RandomCriteria randomCriteria;

    private Integer problemCount;

    private Long timeLimit;
    private RandomDefense(RandomCriteria randomCriteria, Integer problemCount, Long timeLimit, String contentName) {
        super(contentName, RANDOM);
        this.randomCriteria = randomCriteria;
        this.problemCount = isValidProblemCount(problemCount);
        this.timeLimit = isValidTimeLimit(timeLimit);
    }
    public static RandomDefense create(RandomCriteria randomCriteria, Integer problemCount, Long timeLimit, String contentName) {
        return new RandomDefense(randomCriteria, problemCount, timeLimit, contentName);
    }
    private Integer isValidProblemCount(Integer problemCount) {
        if (problemCount <= 0) {
            throw new IllegalArgumentException("랜덤 디펜스 문제 수는 1문제 이상 이어야 합니다.");
        }
        return problemCount;
    }
    private Long isValidTimeLimit(Long timeLimit) {
        if (timeLimit <= 0) {
            throw new IllegalArgumentException("랜덤 디펜스 제한 시간은 0보다 커야 합니다.");
        }
        return timeLimit;
    }
}
