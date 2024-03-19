package kr.co.morandi.backend.domain.defense.dailydefense.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.problem.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static kr.co.morandi.backend.domain.defense.DefenseType.DAILY;

@Entity
@DiscriminatorValue("DailyDefense")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DailyDefense extends Defense {

    private LocalDate date;

    private Integer problemCount;

    @OneToMany(mappedBy = "dailyDefense", cascade = CascadeType.ALL)
    List<DailyDefenseProblem> dailyDefenseProblems = new ArrayList<>();

    private DailyDefense(LocalDate date, String contentName, List<Problem> problems) {
        super(contentName, DAILY);
        this.date = date;
        AtomicLong problemNumber = new AtomicLong(1);
        this.dailyDefenseProblems = problems.stream()
                .map(problem -> DailyDefenseProblem.create(this, problem, problemNumber.getAndIncrement()))
                .toList();
        this.problemCount = problems.size();
    }
    public static DailyDefense create(LocalDate date, String contentName, List<Problem> problems) {
        return new DailyDefense(date, contentName, problems);
    }

}
