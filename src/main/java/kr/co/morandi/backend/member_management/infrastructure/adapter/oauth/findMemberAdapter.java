package kr.co.morandi.backend.member_management.infrastructure.adapter.oauth;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.AuthErrorCode;
import kr.co.morandi.backend.member_management.application.port.out.oauth.FindMemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class findMemberAdapter implements FindMemberPort {

    private final MemberRepository memberRepository;
    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MorandiException(AuthErrorCode.MEMBER_NOT_FOUND));
    }
}
