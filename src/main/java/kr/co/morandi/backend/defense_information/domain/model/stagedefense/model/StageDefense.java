package kr.co.morandi.backend.defense_information.domain.model.stagedefense.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.RandomCriteria;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.STAGE;

@Entity
@DiscriminatorValue("StageDefense")
@Getter
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

    @Builder
    private StageDefense(RandomCriteria randomCriteria, Long timeLimit, String contentName) {
        super(contentName, STAGE);
        this.randomCriteria = randomCriteria;
        this.averageStage = 0.0;
        this.timeLimit = isValidTimeLimit(timeLimit);
    }

}
