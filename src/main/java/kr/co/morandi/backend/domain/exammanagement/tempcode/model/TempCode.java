package kr.co.morandi.backend.domain.exammanagement.tempcode.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.exammanagement.sessiondetail.model.SessionDetail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempCode extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempCodeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private SessionDetail sessionDetail;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(columnDefinition = "TEXT")
    private String code;

    public static TempCode create(Language language, String code, SessionDetail sessionDetail) {
        return TempCode.builder()
                .language(language)
                .code(code)
                .sessionDetail(sessionDetail)
                .build();
    }

    public void updateTempCode(String code) {
        this.code = code;
    }

    @Builder
    private TempCode(SessionDetail sessionDetail, Language language, String code) {
        this.sessionDetail = sessionDetail;
        this.language = language;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempCode tempCode = (TempCode) o;

        return getLanguage() == tempCode.getLanguage();
    }
    @Override
    public int hashCode() {
        return getLanguage() != null ? getLanguage().hashCode() : 0;
    }
}
