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
    private Member makeMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}