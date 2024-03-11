package kr.co.morandi.backend.domain.bookmark;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.member.Member;
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
