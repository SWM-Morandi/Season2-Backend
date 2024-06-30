package kr.co.morandi.backend.defense_record.domain.model.customdefense_record;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_information.domain.model.customdefense.CustomDefense;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_record.domain.model.record.Record;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("CustomRecord")
public class CustomRecord extends Record<CustomDetail> {

    private Integer problemCount;
    @Override
    public CustomDetail createDetail(Member member, Long sequenceNumber, Problem problem, Record<CustomDetail> records, Defense defense) {
        return CustomDetail.create(member, sequenceNumber, problem, records, defense);
    }

    @Builder
    private CustomRecord(CustomDefense customDefense, Member member, LocalDateTime testDate, Map<Long, Problem> problems) {
        super(testDate, customDefense, member, problems);
        this.problemCount = customDefense.getProblemCount();
    }
    public static CustomRecord create(CustomDefense customDefense, Member member, LocalDateTime testDate, Map<Long, Problem> problems) {
        return new CustomRecord(customDefense, member, testDate, problems);
    }
}
