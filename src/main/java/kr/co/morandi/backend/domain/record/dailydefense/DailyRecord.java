package kr.co.morandi.backend.domain.record.dailydefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.defense.dailydefense.DailyDefense;
import kr.co.morandi.backend.domain.detail.dailydefense.DailyDetail;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DailyDefenseRecord")
public class DailyRecord extends Record {
    private Long solvedCount;
    private Integer problemCount;

    private DailyRecord(LocalDateTime date, Defense defense,
                            Member member, List<Problem> problems) {
        super(date, defense, member, problems);
        this.solvedCount = 0L;
        this.problemCount = problems.size();
    }
    @Override
    protected Detail createDetail(Member member, Problem problem, Record record, Defense defense) {
        return DailyDetail.create(member, problem, record, defense);
    }
    public static DailyRecord create(LocalDateTime date, DailyDefense DailyDefense,
                                         Member member, List<Problem> problems) {

        if (Duration.between(DailyDefense.getDate(), date).toDays() >= 1)
            throw new IllegalArgumentException("오늘의 문제 기록은 출제 시점으로부터 하루 이내에 생성되어야 합니다.");

        return new DailyRecord(date, DailyDefense, member, problems);
    }
}
