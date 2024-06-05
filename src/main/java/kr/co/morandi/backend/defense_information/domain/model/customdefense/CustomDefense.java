package kr.co.morandi.backend.defense_information.domain.model.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_information.domain.model.defense.DefenseTier;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.CUSTOM;

@Entity
@DiscriminatorValue("CustomDefense")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefense extends Defense {

    private LocalDateTime createDate;

    private Integer problemCount;

    private String description;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Enumerated(EnumType.STRING)
    private DefenseTier defenseTier;

    private Long timeLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "customDefense", cascade = CascadeType.ALL)
    private List<CustomDefenseProblem> customDefenseProblems = new ArrayList<>();

    @Override
    public LocalDateTime getEndTime(LocalDateTime startTime) {
        return startTime.plusMinutes(timeLimit);
    }

    public static CustomDefense create(List<Problem> problems, Member member, String contentName, String description, Visibility visibility, DefenseTier defenseTier, Long timeLimit, LocalDateTime createDate) {
        return new CustomDefense(problems, member, contentName, description, visibility, defenseTier, timeLimit, createDate);
    }

    private Long isValidTimeLimit(Long timeLimit) {
        if (timeLimit < 0) {
            throw new IllegalArgumentException("커스텀 디펜스 제한 시간은 0보다 커야 합니다.");
        }
        return timeLimit;
    }

    private int isValidProblemCount(int problemCount) {
        if (problemCount < 1) {
            throw new IllegalArgumentException("커스텀 디펜스에는 최소 한 개의 문제가 포함되어야 합니다.");
        }
        return problemCount;
    }

    private CustomDefense(List<Problem> problems, Member member, String contentName, String description,
                          Visibility visibility, DefenseTier defenseTier, Long timeLimit, LocalDateTime createDate) {
        super(contentName, CUSTOM);
        this.problemCount = isValidProblemCount(problems.size());
        this.timeLimit = isValidTimeLimit(timeLimit);
        this.description = description;
        this.visibility = visibility;
        this.defenseTier = defenseTier;
        this.member = member;
        AtomicLong problemNumber = new AtomicLong(1);
        this.customDefenseProblems = problems.stream()
                .map(problem -> CustomDefenseProblem.create(this, problemNumber.getAndIncrement(), problem))
                .toList();
        this.createDate = createDate;
    }
}
