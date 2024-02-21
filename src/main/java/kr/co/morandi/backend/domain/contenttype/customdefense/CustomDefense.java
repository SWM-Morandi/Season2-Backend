package kr.co.morandi.backend.domain.contenttype.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("CustomDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefense extends ContentType {

    private LocalDateTime createDate;

    private int problemCount;

    private String description;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Enumerated(EnumType.STRING)
    private DefenseTier defenseTier;

    private Long timeLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "customDefense", cascade = CascadeType.ALL)
    private List<CustomDefenseProblems> customDefenseProblems = new ArrayList<>();

    private CustomDefense(List<Problem> problems, Member member, String contentName, String description, Visibility visibility, DefenseTier defenseTier, Long timeLimit, LocalDateTime createDate) {
        super(contentName);
        this.problemCount = isValidProblemCount(problems.size());
        this.timeLimit = isValidTimeLimit(timeLimit);
        this.description = description;
        this.visibility = visibility;
        this.defenseTier = defenseTier;
        this.member = member;
        this.customDefenseProblems = problems.stream()
                .map(problem -> new CustomDefenseProblems(this, problem))
                .collect(Collectors.toList());
        this.createDate = createDate;
    }

    public static CustomDefense create(List<Problem> problems, Member member,String contentName,  String description, Visibility visibility, DefenseTier defenseTier, Long timeLimit, LocalDateTime createDate) {
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

}
