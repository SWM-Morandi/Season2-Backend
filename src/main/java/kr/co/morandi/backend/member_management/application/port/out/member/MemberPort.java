package kr.co.morandi.backend.member_management.application.port.out.member;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.constants.SocialType;

import java.util.Optional;

public interface MemberPort {
    Member saveMemberByEmail(String email, SocialType type);
    Member findMemberById(Long memberId);
    Optional<Member> findMemberByEmail(String email);
}
