package kr.co.morandi.backend.domain.bookmark;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class BookMark extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private BookMark(ContentType contentType, Member member) {
        this.contentType = contentType;
        this.member = member;
    }
}
