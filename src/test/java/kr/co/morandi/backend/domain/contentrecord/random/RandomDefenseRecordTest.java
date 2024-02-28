package kr.co.morandi.backend.domain.contentrecord.random;

import kr.co.morandi.backend.domain.contenttype.random.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.contenttype.random.randomdefense.RandomDefense;
import kr.co.morandi.backend.domain.contenttype.random.randomdefense.RandomDefenseRepository;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.G5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RandomDefenseRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RandomDefenseRepository randomDefenseRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        randomDefenseRepository.deleteAllInBatch();
    }
    @DisplayName("사용자가 랜덤 디펜스를 시작했을 때, 맞춘 문제수는 0문제이고 총 문제수는 랜덤 디펜스 문제 개수와 같아야 한다.")
    @Test
    void solvedCountIsZeroAndProblemCountEqual() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomDefense randomDefense = RandomDefense.create(randomCriteria, 4L, 120L, "브론즈 랜덤 디펜스");
        List<Problem> problems = createProblems();
        Member member = makeMember("user");
        LocalDateTime now = LocalDateTime.now();

        // when
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());

        // then
        assertThat(randomDefenseRecord)
                .extracting("solvedCount", "problemCount")
                .containsExactly(0L, randomDefense.getProblemCount());

    }
    @DisplayName("사용자가 랜덤 디펜스를 시작했을 때, 전체 소요 시간은 0분 이어야 한다.")
    @Test
    void totalSolvedTimeIsZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomDefense randomDefense = RandomDefense.create(randomCriteria, 4L, 120L, "브론즈 랜덤 디펜스");
        List<Problem> problems = createProblems();
        Member member = makeMember("user");
        LocalDateTime now = LocalDateTime.now();

        // when
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());

        // then
        assertThat(randomDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("사용자가 랜덤 디펜스를 시작한 시점과 랜덤 디펜스 시험 날짜는 같아야 한다.")
    @Test
    void testDateIsEqualNow() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomDefense randomDefense = RandomDefense.create(randomCriteria, 4L, 120L, "브론즈 랜덤 디펜스");
        List<Problem> problems = createProblems();
        Member member = makeMember("user");
        LocalDateTime now = LocalDateTime.now();

        // when
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());

        // then
        assertThat(randomDefenseRecord.getTestDate()).isEqualTo(now);
    }


    private Member makeMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        Problem problem4 = Problem.create(4L, P5, 0L);
        List<Problem> problems = List.of(problem1, problem2, problem3, problem4);
        problemRepository.saveAll(problems);
        return problems;
    }
}