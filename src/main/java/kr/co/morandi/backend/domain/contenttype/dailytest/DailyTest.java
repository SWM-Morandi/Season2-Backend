package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DailyTest")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyTest extends ContentType {

    private LocalDateTime date;
}
