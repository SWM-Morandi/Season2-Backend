package kr.co.morandi.backend.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);
}
