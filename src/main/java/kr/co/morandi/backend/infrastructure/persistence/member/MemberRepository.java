package kr.co.morandi.backend.infrastructure.persistence.member;

import kr.co.morandi.backend.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByNickname(String nickname);

}
