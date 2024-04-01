package kr.co.morandi.backend.member_management.infrastructure.adapter.member;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.exception.errorcode.AuthErrorCode;
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.SocialType;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAdapter implements MemberPort {

    private final MemberRepository memberRepository;
    @Override
    public Member createMember(String email, SocialType type) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .email(email)
                                .socialType(type)
                                .build()));
    }
    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MorandiException(AuthErrorCode.MEMBER_NOT_FOUND));
    }
}
