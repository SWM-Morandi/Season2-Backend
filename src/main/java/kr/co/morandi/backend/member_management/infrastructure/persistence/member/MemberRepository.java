package kr.co.morandi.backend.member_management.infrastructure.persistence.member;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);

    @Query("""
        select m
        from Member m
        left join fetch m.baekjoonMemberCookie
        where m.memberId = :memberId
    """)
    Optional<Member> findMemberJoinFetchCookie(Long memberId);
}
