package kr.co.morandi.backend.domain.record.customdefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.model.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.detail.customdefense.CustomDetail;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("CustomRecord")
public class CustomRecord extends Record {
    private Long totalSolvedTime;
    private Integer solvedCount;
    private Integer problemCount;
    @Override
    public Detail createDetail(Member member, Problem problem,
                               Record record, Defense defense) {
        return CustomDetail.create(member, problem, record, defense);
    }
    private CustomRecord(CustomDefense customDefense, Member member, LocalDateTime testDate,
                                List<Problem> problems) {
        super(testDate, customDefense, member, problems);
        this.totalSolvedTime = 0L;
        this.solvedCount = 0;
        this.problemCount = customDefense.getProblemCount();
    }
    public static CustomRecord create(CustomDefense customDefense, Member member, LocalDateTime testDate,
                                             List<Problem> problems) {
        return new CustomRecord(customDefense, member, testDate, problems);
    }
}
