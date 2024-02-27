package kr.co.morandi.backend.domain.contenttype.customdefense;

import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static kr.co.morandi.backend.domain.contenttype.customdefense.DefenseTier.GOLD;
import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.OPEN;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomDefenseTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomDefenseProblemsRepository customDefenseProblemsRepository;

    @AfterEach
    void tearDown() {
        customDefenseProblemsRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("커스텀 디펜스를 생성하면 등록 시간을 기록한다.")
    @Test
    void registeredWithDateTime() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        List<Problem> problems = List.of(problem1, problem2);
        problemRepository.saveAll(problems);

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        // when
        CustomDefense customDefense = CustomDefense.create(problems, member, "커스텀 디펜스1", "커스텀 디펜스1 설명", OPEN, GOLD, 60L, now);

        // then
        assertThat(customDefense.getCreateDate()).isEqualTo(now);

    }
    @DisplayName("커스텀 디펜스를 생성하면 컨텐츠 이름과 설명을 기록한다.")
    @Test
    void createCustomDefenseWithContentName() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        List<Problem> problems = List.of(problem1, problem2);
        problemRepository.saveAll(problems);

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        // when
        CustomDefense customDefense = CustomDefense.create(problems, member, "커스텀 디펜스1","커스텀 디펜스1 설명", OPEN, GOLD, 60L, now);

        // then
        assertThat(customDefense)
                .extracting("contentName", "description")
                .containsExactlyInAnyOrder(
                        "커스텀 디펜스1", "커스텀 디펜스1 설명");
    }

    @DisplayName("커스텀 디펜스에 포함된 문제 개수를 조회할 수 있다.")
    @Test
    void createCustomDefenseProblemCount() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        List<Problem> problems = createProblems();

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        // when
        CustomDefense customDefense = CustomDefense.create(problems, member, "커스텀 디펜스1", "커스텀 디펜스1 설명", OPEN, GOLD, 60L, now);

        // then
        assertThat(customDefense.getCustomDefenseProblems())
                .hasSize(3)
                .extracting("problem.baekjoonProblemId", "problem.problemTier")
                .containsExactlyInAnyOrder(
                        tuple(1L, B5),
                        tuple(2L, S5),
                        tuple(3L, G5)
                );
    }
    @DisplayName("커스텀 디펜스를 빈 문제 리스트로 생성하면 예외가 발생한다")
    @Test
    void createCustomDefenseWithoutProblem() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        List<Problem> problems = Collections.emptyList();

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        // when & then
        assertThatThrownBy( () -> CustomDefense.create(problems, member, "커스텀 디펜스1", "커스텀 디펜스1 설명", OPEN, GOLD, 60L, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커스텀 디펜스에는 최소 한 개의 문제가 포함되어야 합니다.");

    }

    @DisplayName("커스텀 디펜스 제한 시간을 0으로 설정하면 예외를 발생한다.")
    @Test
    void createCustomDefenseWithZeroTimeLimit() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        List<Problem> problems = createProblems();

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        // when & then
        assertThatThrownBy( () -> CustomDefense.create(problems, member, "커스텀 디펜스1", "커스텀 디펜스1 설명", OPEN, GOLD, -1L, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커스텀 디펜스 제한 시간은 0보다 커야 합니다.");
    }

    @DisplayName("커스텀 디펜스 제한 시간을 0 미만의 값으로 설정하면 예외를 발생한다.")
    @Test
    void createCustomDefenseWithNegativeTimeLimit() {
        // given
        Member member = Member.create("test1", "test1", GOOGLE, "test1", "test1");
        memberRepository.save(member);

        List<Problem> problems = createProblems();

        LocalDateTime now = LocalDateTime.of(2024, 2, 21, 0, 0, 0, 0);

        // when & then
        assertThatThrownBy( () -> CustomDefense.create(problems, member, "커스텀 디펜스1", "커스텀 디펜스1 설명", OPEN, GOLD, -1L, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("커스텀 디펜스 제한 시간은 0보다 커야 합니다.");

    }

    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        List<Problem> problems = List.of(problem1, problem2, problem3);
        problemRepository.saveAll(problems);
        return problems;
    }

}