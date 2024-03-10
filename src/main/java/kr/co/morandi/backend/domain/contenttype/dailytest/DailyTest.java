package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.co.morandi.backend.domain.contenttype.Defense;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("DailyTest")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DailyTest extends Defense {

    private LocalDateTime date;

    private Integer problemCount;

    @OneToMany(mappedBy = "dailyTest", cascade = CascadeType.ALL)
    List<DailyTestProblems> dailyTestProblemsList = new ArrayList<>();

    private DailyTest(LocalDateTime date, String contentName, List<Problem> problems) {
        super(contentName);
        this.date = date;
        this.dailyTestProblemsList = problems.stream()
                .map(problem -> DailyTestProblems.create(this, problem))
                .toList();
        this.problemCount = problems.size();
    }
    public static DailyTest create(LocalDateTime date, String contentName, List<Problem> problems) {
        return new DailyTest(date, contentName, problems);
    }
}
