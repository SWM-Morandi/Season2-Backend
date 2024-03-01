package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("DailyTest")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyTest extends ContentType {

    private LocalDateTime date;

    private Long problemCount;

    @OneToMany(mappedBy = "dailyTest", cascade = CascadeType.ALL)
    List<DailyTestProblems> dailyTestProblemsList = new ArrayList<>();
    private DailyTest(LocalDateTime date, String contentName, List<Problem> problems) {
        super(contentName);
        this.date = date;
        this.dailyTestProblemsList = problems.stream()
                .map(problem -> DailyTestProblems.create(this, problem))
                .collect(Collectors.toList());
    }
    public static DailyTest create(LocalDateTime date, String contentName, List<Problem> problems) {
        return new DailyTest(date, contentName, problems);
    }
}
