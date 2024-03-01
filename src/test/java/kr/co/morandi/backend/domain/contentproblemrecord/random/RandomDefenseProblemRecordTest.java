package kr.co.morandi.backend.domain.contentproblemrecord.random;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.random.RandomDefenseRecord;
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
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RandomDefenseProblemRecordTest {

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
    @DisplayName("사용자가 랜덤 디펜스를 시작했을 때, 문제 풀이 기록의 정답 여부는 모두 오답이어야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        RandomDefense randomDefense = getRandomDefense();
        List<Problem> problems = createProblems();
        Member member = getMember("user");
        LocalDateTime now = LocalDateTime.now();
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());
        List<ContentProblemRecord> contentProblemRecords = randomDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("isSolved")
                .containsExactly(false, false, false, false);
    }

    @DisplayName("사용자가 랜덤 디펜스를 시작했을 때, 문제 풀이 기록의 제출 횟수는 모두 0회여야 한다.")
    @Test
    void submitCountIsZero() {
        // given
        RandomDefense randomDefense = getRandomDefense();
        List<Problem> problems = createProblems();
        Member member = getMember("user");
        LocalDateTime now = LocalDateTime.now();
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());
        List<ContentProblemRecord> contentProblemRecords = randomDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("submitCount")
                .containsExactly(0L, 0L, 0L, 0L);
    }
    @DisplayName("사용자가 랜덤 디펜스를 시작했을 때, 문제 풀이 기록의 정답 코드는 모두 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        RandomDefense randomDefense = getRandomDefense();
        List<Problem> problems = createProblems();
        Member member = getMember("user");
        LocalDateTime now = LocalDateTime.now();
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());
        List<ContentProblemRecord> contentProblemRecords = randomDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("solvedCode")
                .containsExactly(null, null, null, null);
    }
    @DisplayName("사용자가 랜덤 디펜스를 시작했을 때, 문제 풀이 기록의 소요 시간은 모두 0분 이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        RandomDefense randomDefense = getRandomDefense();
        List<Problem> problems = createProblems();
        Member member = getMember("user");
        LocalDateTime now = LocalDateTime.now();
        RandomDefenseRecord randomDefenseRecord
                = RandomDefenseRecord.create(randomDefense, member, now, problems, randomDefense.getProblemCount());
        List<ContentProblemRecord> contentProblemRecords = randomDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("solvedTime")
                .containsExactly(0L, 0L, 0L, 0L);
    }
    private static RandomDefense getRandomDefense() {
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomDefense randomDefense = RandomDefense.create(randomCriteria, 4L, 120L, "브론즈 랜덤 디펜스");
        return randomDefense;
    }
    private Member getMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, B4, 0L);
        Problem problem3 = Problem.create(3L, B3, 0L);
        Problem problem4 = Problem.create(3L, B2, 0L);
        List<Problem> problems = List.of(problem1, problem2, problem3, problem4);
        problemRepository.saveAll(problems);
        return problems;
    }
}