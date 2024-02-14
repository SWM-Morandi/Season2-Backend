package kr.co.morandi.backend.domain.bookmark;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookMark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
