package kr.co.morandi.backend.domain.contentmemberlikes;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.member.Member;
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
