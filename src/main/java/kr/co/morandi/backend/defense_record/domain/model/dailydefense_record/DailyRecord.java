package kr.co.morandi.backend.defense_record.domain.model.dailydefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyDefenseRecord")
public class DailyRecord extends Record<DailyDetail> {


    private Integer problemCount;

    public Set<Long> getSolvedProblemNumbers() {
        return super.getDetails().stream()
                .filter(DailyDetail::getIsSolved)
                .map(DailyDetail::getProblemNumber)
                .collect(Collectors.toSet());
    }
    public boolean isSolvedProblem(Long problemNumber) {
        return super.getDetails().stream()
                .anyMatch(detail -> detail.getProblemNumber().equals(problemNumber)
                        && detail.getIsSolved());
    }
    @Override
    protected DailyDetail createDetail(Member member, Long sequenceNumber, Problem problem, Record<DailyDetail> records, Defense defense) {
        return DailyDetail.create(member, sequenceNumber, problem, records, defense);
    }

    public static DailyRecord tryDefense(LocalDateTime date, DailyDefense dailyDefense, Member member, Map<Long, Problem> problems) {

        if (!date.toLocalDate().equals(dailyDefense.getDate())) {
            throw new IllegalArgumentException("오늘의 문제 기록은 출제 날짜와 같은 날에 생성되어야 합니다.");
        }

        return new DailyRecord(date, dailyDefense, member, problems);
    }

    public void tryMoreProblem(Map<Long, Problem> problem) {
        // 이미 시도한 문제들의 problemId를 가져오고
        final Set<Long> collect = super.getDetails().stream()
                .map(Detail::getProblem)
                .map(Problem::getProblemId)
                .collect(Collectors.toSet());

        // 시도하려는 문제들 중 이미 시도한 문제들을 제외한 문제들만 추가
        final List<DailyDetail> newDetails = problem.entrySet().stream()
                .filter(entry -> !collect.contains(entry.getValue().getProblemId()))
                .map(p -> createDetail(this.getMember(), p.getKey(), p.getValue(), this, this.getDefense()))
                .toList();

        // 문제 추가
        super.getDetails().addAll(newDetails);

        // 새로운 문제 추가로 문제 수 증가
        this.problemCount += newDetails.size();
    }

    @Builder
    private DailyRecord(LocalDateTime date, Defense defense, Member member, Map<Long, Problem> problems) {
        super(date, defense, member, problems);
        this.problemCount = problems.size();
        defense.increaseAttemptCount();
    }

}
