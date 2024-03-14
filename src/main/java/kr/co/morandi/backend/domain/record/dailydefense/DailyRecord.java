package kr.co.morandi.backend.domain.record.dailydefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.defense.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.detail.dailydefense.DailyDetail;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyDefenseRecord")
public class DailyRecord extends Record {

    private Long solvedCount;
    private Integer problemCount;

    private DailyRecord(LocalDateTime date, Defense defense, Member member, Map<Long, Problem> problems) {
        super(date, defense, member, problems);
        this.solvedCount = 0L;
        this.problemCount = problems.size();
    }
    @Override
    protected Detail createDetail(Member member, Long sequenceNumber, Problem problem, Record record, Defense defense) {
        return DailyDetail.create(member, sequenceNumber, problem, record, defense);
    }

    public static DailyRecord tryDefense(LocalDateTime date, DailyDefense dailyDefense, Member member, Map<Long, Problem> problems) {

        if (!date.toLocalDate().equals(dailyDefense.getDate())) {
            throw new IllegalArgumentException("오늘의 문제 기록은 출제 날짜와 같은 날에 생성되어야 합니다.");
        }

        return new DailyRecord(date, dailyDefense, member, problems);
    }

    public DailyRecord tryMoreProblem(Map<Long, Problem> problem) {
        // 이미 시도한 문제들의 problemId를 가져오고
        final Set<Long> collect = super.getDetails().stream()
                .map(Detail::getProblem)
                .map(Problem::getProblemId)
                .collect(Collectors.toSet());

        // 시도하려는 문제들 중 이미 시도한 문제들을 제외한 문제들만 추가
        final List<Detail> newDetails = problem.entrySet().stream()
                .filter(entry -> !collect.contains(entry.getValue().getProblemId()))
                .map(p -> createDetail(this.getMember(), p.getKey(), p.getValue(), this, this.getDefense()))
                .toList();

        // 문제 추가
        super.getDetails().addAll(newDetails);

        // 새로운 문제 추가로 문제 수 증가
        this.problemCount += newDetails.size();

        return this;
    }

}
