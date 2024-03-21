package kr.co.morandi.backend.domain.defense.stagedefense.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.random.model.randomcriteria.RandomCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static kr.co.morandi.backend.domain.defense.DefenseType.STAGE;

@Entity
@DiscriminatorValue("StageDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StageDefense extends Defense {

    @Embedded
    private RandomCriteria randomCriteria;

    private Double averageStage;

    private Long timeLimit;

    @Override
    public LocalDateTime getEndTime(LocalDateTime startTime) {
        return startTime.plusMinutes(timeLimit);
    }

    public static StageDefense create(RandomCriteria randomCriteria, Long timeLimit, String contentName) {
        return new StageDefense(randomCriteria, timeLimit, contentName);
    }

    private Long isValidTimeLimit(Long timeLimit) {
        if (timeLimit <= 0) {
            throw new IllegalArgumentException("스테이지 모드 제한 시간은 0보다 커야 합니다.");
        }
        return timeLimit;
    }

    private StageDefense(RandomCriteria randomCriteria, Long timeLimit, String contentName) {
        super(contentName, STAGE);
        this.randomCriteria = randomCriteria;
        this.averageStage = 0.0;
        this.timeLimit = isValidTimeLimit(timeLimit);
    }

}
