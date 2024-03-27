package kr.co.morandi.backend.domain.defense.dailydefense.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.problemgenerationstrategy.service.ProblemGenerationService;
import kr.co.morandi.backend.domain.problem.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Builder.Default
    @OneToMany(mappedBy = "dailyDefense", cascade = CascadeType.ALL)
    List<DailyDefenseProblem> dailyDefenseProblems = new ArrayList<>();

    @Override
    public LocalDateTime getEndTime(LocalDateTime startTime) {
        //시작 날까지
        return date.atStartOfDay().plusDays(1).minusSeconds(1);
    }
    public Map<Long, Problem> getTryingProblem(Long problemNumber, ProblemGenerationService problemGenerationService) {
        Map<Long, Problem> tryProblem = this.getDefenseProblems(problemGenerationService).entrySet().stream()
                .filter(p -> p.getKey().equals(problemNumber))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (tryProblem.isEmpty()) {
            throw new IllegalArgumentException("해당 문제가 오늘의 문제 목록에 없습니다.");
        }
        return tryProblem;
    }

    private DailyDefense(LocalDate date, String contentName, Map<Long, Problem> problems) {
        super(contentName, DAILY);
        this.date = date;
        this.dailyDefenseProblems = problems.entrySet().stream()
                .map(problem -> DailyDefenseProblem.create(this, problem.getValue(), problem.getKey()))
                .toList();

        this.problemCount = problems.size();
    }
    public static DailyDefense create(LocalDate date, String contentName, Map<Long, Problem> problems) {
        return new DailyDefense(date, contentName, problems);
    }


}
