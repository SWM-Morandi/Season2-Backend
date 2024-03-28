package kr.co.morandi.backend.member_management.infrastructure.persistence.member;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByNickname(String nickname);

}
