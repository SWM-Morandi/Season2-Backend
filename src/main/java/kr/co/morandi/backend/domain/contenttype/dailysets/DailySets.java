package kr.co.morandi.backend.domain.contenttype.dailysets;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DailySets")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailySets extends ContentType {
    private LocalDateTime testDate;
}
