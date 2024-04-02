package kr.co.morandi.backend.member_management.application.port.out.member;

import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.SocialType;

public interface MemberPort {
    Member saveMember(Member member);
    Member findMemberById(Long memberId);
    Member findMemberByEmail(String email, SocialType type);
}
