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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return DailyDetail.create(member, problem, record, defense);
    }
    public static DailyRecord create(LocalDateTime date, DailyDefense dailyDefense, Member member, Map<Long, Problem> problems) {

        if (!date.toLocalDate().equals(dailyDefense.getDate())) {
            throw new IllegalArgumentException("오늘의 문제 기록은 출제 날짜와 같은 날에 생성되어야 합니다.");
        }

        return new DailyRecord(date, dailyDefense, member, problems);
    }
}
