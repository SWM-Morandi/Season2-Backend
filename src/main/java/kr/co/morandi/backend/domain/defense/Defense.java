package kr.co.morandi.backend.domain.defense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.problemgenerationstrategy.service.ProblemGenerationService;
import kr.co.morandi.backend.domain.problem.model.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Defense extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defenseId;

    private String contentName;

    private Long attemptCount;

    @Enumerated(EnumType.STRING)
    private DefenseType defenseType;

    public abstract LocalDateTime getEndTime(LocalDateTime startTime);
    //팩토리 메소드 패턴
    public Map<Long, Problem> getDefenseProblems(ProblemGenerationService problemGenerationService) {
        return problemGenerationService.getDefenseProblems(this);
    }
    public DefenseType getType() {
        return defenseType;
    }
    protected Defense(String contentName, DefenseType defenseType) {
        this.contentName = contentName;
        this.attemptCount = 0L;
        this.defenseType = defenseType;
    }

}
