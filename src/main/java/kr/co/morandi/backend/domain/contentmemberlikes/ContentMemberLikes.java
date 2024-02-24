package kr.co.morandi.backend.domain.contentmemberlikes;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.contenttype.ContentType;
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
    private ContentType contentType;

    @Builder
    private ContentMemberLikes(Member member, ContentType contentType) {
        this.member = member;
        this.contentType = contentType;
    }
}
