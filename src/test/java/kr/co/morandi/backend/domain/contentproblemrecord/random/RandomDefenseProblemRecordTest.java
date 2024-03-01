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