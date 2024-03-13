package kr.co.morandi.backend.domain.defense.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.service.ProblemGenerationService;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
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

    private DefenseType defenseType;

    //팩토리 메소드 패턴
    public Map<Long, Problem> getDefenseProblems(ProblemGenerationService problemGenerationService) {
        return problemGenerationService.getDefenseProblems(this);
    }
    public DefenseType getType() {
        return defenseType;
    }
    public Defense(String contentName, DefenseType defenseType) {
        this.contentName = contentName;
        this.attemptCount = 0L;
        this.defenseType = defenseType;
    }

}
