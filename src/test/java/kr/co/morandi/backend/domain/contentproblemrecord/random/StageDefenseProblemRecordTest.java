package kr.co.morandi.backend.domain.contentproblemrecord.random;

import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contentrecord.random.StageDefenseRecord;
import kr.co.morandi.backend.domain.contenttype.random.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.contenttype.random.randomstagedefense.RandomStageDefense;
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

import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B1;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class StageDefenseProblemRecordTest {

    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 만들어진 첫번째 문제 기록의 스테이지 번호는 1번이어야 한다.")
    @Test
    void startStageNumberIsOne() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("stageNumber")
                .containsExactly(1L);
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 만들어진 첫번째 문제 기록의 소요 시간은 0분 이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("solvedTime")
                .containsExactly(0L);
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 만들어진 첫번째 문제 기록의 정답 여부는 오답이어야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("isSolved")
                .containsExactly(false);
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 만들어진 첫번째 문제 기록의 제출 횟수는 0회 이어야한다.")
    @Test
    void submitCountIsZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("submitCount")
                .containsExactly(0L);
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 만들어진 첫번째 문제 기록의 정답 코드는 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));
        List<ContentProblemRecord> contentProblemRecords = stageDefenseRecord.getContentProblemRecords();

        // when & then
        assertThat(contentProblemRecords)
                .extracting("solvedCode")
                .containsExactly((String)null);
    }
    private Member makeMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}