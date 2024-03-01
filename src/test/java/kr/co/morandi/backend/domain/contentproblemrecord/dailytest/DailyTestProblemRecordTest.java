package kr.co.morandi.backend.domain.contentproblemrecord.dailytest;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.dailytest.DailyTestRecord;
import kr.co.morandi.backend.domain.contenttype.dailytest.DailyTest;
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
class DailyTestProblemRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        Problem problem3 = Problem.create(3L, G5, 0L);
        List<Problem> problems = List.of(problem1, problem2, problem3);
        problemRepository.saveAll(problems);
        return problems;
    }
    private Member createMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}