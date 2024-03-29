package kr.co.morandi.backend.defense_information.domain.model.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class BookMark extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Defense defense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private BookMark(Defense defense, Member member) {
        this.defense = defense;
        this.member = member;
    }
}
