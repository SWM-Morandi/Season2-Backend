package kr.co.morandi.backend.domain.contenttype.random.randomstagedefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.Defense;
import kr.co.morandi.backend.domain.contenttype.random.randomcriteria.RandomCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("StageDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomStageDefense extends Defense {

    @Embedded
    private RandomCriteria randomCriteria;

    private Double averageStage;

    private Long timeLimit;
    private RandomStageDefense(RandomCriteria randomCriteria, Long timeLimit, String contentName) {
        super(contentName);
        this.randomCriteria = randomCriteria;
        this.averageStage = 0.0;
        this.timeLimit = isValidTimeLimit(timeLimit);
    }
    public static RandomStageDefense create(RandomCriteria randomCriteria, Long timeLimit, String contentName) {
        return new RandomStageDefense(randomCriteria, timeLimit, contentName);
    }
    private Long isValidTimeLimit(Long timeLimit) {
        if (timeLimit <= 0) {
            throw new IllegalArgumentException("스테이지 모드 제한 시간은 0보다 커야 합니다.");
        }
        return timeLimit;
    }
}
