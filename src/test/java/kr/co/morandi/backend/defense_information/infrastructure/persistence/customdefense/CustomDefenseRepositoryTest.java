package kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense;

import kr.co.morandi.backend.defense_information.domain.model.customdefense.CustomDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense.CustomDefenseProblemRepository;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.customdefense.CustomDefenseRepository;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseTier.*;
import static kr.co.morandi.backend.defense_information.domain.model.customdefense.Visibility.CLOSE;
import static kr.co.morandi.backend.defense_information.domain.model.customdefense.Visibility.OPEN;
import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@ActiveProfiles("test")
class CustomDefenseRepositoryTest {

    @Autowired
    private CustomDefenseRepository customDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomDefenseProblemRepository customDefenseProblemsRepository;
    @AfterEach
    void tearDown() {
        customDefenseProblemsRepository.deleteAllInBatch();
        customDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("공개 상태의 커스텀 디펜스를 조회할 수 있다.")
    @Test
    void findAllByVisibility() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        problemRepository.saveAll(List.of(problem1, problem2, problem3));

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        CustomDefense customDefense1 = CustomDefense.create(List.of(problem1,problem2), member, "커스텀 디펜스1","커스텀 디펜스1 설명", OPEN, BRONZE, 60L, now);
        CustomDefense customDefense2 = CustomDefense.create(List.of(problem1,problem3), member, "커스텀 디펜스2","커스텀 디펜스2 설명", OPEN, SILVER, 60L, now);
        CustomDefense customDefense3 = CustomDefense.create(List.of(problem2,problem3), member, "커스텀 디펜스3","커스텀 디펜스3 설명", CLOSE, GOLD, 60L, now);

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

}