package kr.co.morandi.backend.defense_information.domain.model.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.global.model.BaseEntity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentMemberLikes extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberLikesId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private Defense defense;

    @Builder
    private ContentMemberLikes(Member member, Defense defense) {
        this.member = member;
        this.defense = defense;
    }
}
