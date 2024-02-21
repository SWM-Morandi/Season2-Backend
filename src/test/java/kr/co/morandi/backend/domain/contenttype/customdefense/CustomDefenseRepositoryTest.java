package kr.co.morandi.backend.domain.contenttype.customdefense;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.CLOSE;
import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class CustomDefenseRepositoryTest {

    @Autowired
    private CustomDefenseRepository customDefenseRepository;
    @DisplayName("공개 상태의 커스텀 디펜스를 조회할 수 있다.")
    @Test
    void findAllByVisibility() {
        // given
        CustomDefense customDefense1 = createCustomDefense("커스텀 디펜스1", "커스텀 디펜스1 설명", OPEN);
        CustomDefense customDefense2 = createCustomDefense("커스텀 디펜스2", "커스텀 디펜스2 설명", OPEN);
        CustomDefense customDefense3 = createCustomDefense("커스텀 디펜스3", "커스텀 디펜스3 설명", CLOSE);
        customDefenseRepository.saveAll(List.of(customDefense1, customDefense2, customDefense3));

        // when
        List<CustomDefense> customDefenses = customDefenseRepository.findAllByVisibility(OPEN);

        // then
        assertThat(customDefenses)
                .hasSize(2)
                .extracting("contentName","description","visibility")
                .containsExactlyInAnyOrder(
                        tuple("커스텀 디펜스1","커스텀 디펜스1 설명",OPEN),
                        tuple("커스텀 디펜스2","커스텀 디펜스2 설명",OPEN)
                );

    }

    private CustomDefense createCustomDefense(String contentName, String description, Visibility visibility) {
        return CustomDefense.builder()
                .contentName(contentName)
                .description(description)
                .visibility(visibility)
                .build();
    }


}