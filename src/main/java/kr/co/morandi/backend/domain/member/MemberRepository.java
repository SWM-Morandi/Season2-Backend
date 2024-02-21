package kr.co.morandi.backend.domain.member;


import kr.co.morandi.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
