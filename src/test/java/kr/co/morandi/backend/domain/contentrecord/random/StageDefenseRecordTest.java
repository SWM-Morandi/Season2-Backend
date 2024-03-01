package kr.co.morandi.backend.domain.contentrecord.random;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contenttype.random.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.contenttype.random.randomstagedefense.RandomStageDefense;
import kr.co.morandi.backend.domain.contenttype.tier.ProblemTier;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
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
class StageDefenseRecordTest {

    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 전체 소요 시간은 0분 이어야 한다.")
    @Test
    void totalSolvedTimeIsZero() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, startTime, member, List.of(problem));

        // then
        assertThat(stageDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 스테이지 번호는 1번이어야 한다.")
    @Test
    void stageCountIsOne() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, startTime, member, List.of(problem));

        // then
        assertThat(stageDefenseRecord.getStageCount()).isOne();
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 시험 날짜는 시작한 시점과 같아야 한다.")
    @Test
    void testDateEqualNow() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, startTime, member, List.of(problem));

        // then
        assertThat(stageDefenseRecord.getTestDate()).isEqualTo(startTime);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 스테이지 번호는 1번이어야 한다.")
    @Test
    void startStageNumberIsOne() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, startTime, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("stageNumber")
                .containsExactly(1L);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 소요 시간은 0분 이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("solvedTime")
                .containsExactly(0L);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 정답 여부는 오답이어야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("isSolved")
                .containsExactly(false);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 제출 횟수는 0회 이어야한다.")
    @Test
    void submitCountIsZero() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("submitCount")
                .containsExactly(0L);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 정답 코드는 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        RandomStageDefense randomStageDefense = createRandomStageDefense();
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // then
        assertThat(contentProblemRecords)
                .extracting("solvedCode")
                .containsExactly((String)null);
    }
    private RandomStageDefense createRandomStageDefense() {
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        return randomStageDefense;
    }
    private Member createMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}