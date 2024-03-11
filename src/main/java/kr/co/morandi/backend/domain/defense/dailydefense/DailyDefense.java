package kr.co.morandi.backend.domain.defense.dailydefense;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("DailyDefense")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DailyDefense extends Defense {

    private LocalDateTime date;

    private Integer problemCount;

    @OneToMany(mappedBy = "DailyDefense", cascade = CascadeType.ALL)
    List<DailyDefenseProblem> DailyDefenseProblems = new ArrayList<>();

    private DailyDefense(LocalDateTime date, String contentName, List<Problem> problems) {
        super(contentName);
        this.date = date;
        this.DailyDefenseProblems = problems.stream()
                .map(problem -> DailyDefenseProblem.create(this, problem))
                .toList();
        this.problemCount = problems.size();
    }
    public static DailyDefense create(LocalDateTime date, String contentName, List<Problem> problems) {
        return new DailyDefense(date, contentName, problems);
    }
}
