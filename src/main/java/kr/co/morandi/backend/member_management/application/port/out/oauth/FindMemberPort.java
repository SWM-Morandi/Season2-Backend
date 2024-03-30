package kr.co.morandi.backend.member_management.application.port.out.oauth;

import kr.co.morandi.backend.member_management.domain.model.member.Member;

public interface FindMemberPort {
    Member findMember(Long memberId);
}
