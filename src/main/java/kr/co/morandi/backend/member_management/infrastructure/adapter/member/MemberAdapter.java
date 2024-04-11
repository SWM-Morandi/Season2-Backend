package kr.co.morandi.backend.member_management.infrastructure.adapter.member;

import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.member_management.infrastructure.exception.OAuthErrorCode;
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.config.oauth.constants.SocialType;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberAdapter implements MemberPort {

    private final MemberRepository memberRepository;
    @Override
    public Member saveMemberByEmail(String email, SocialType type) {
        return memberRepository.save(Member.create(email, type));
    }
    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MorandiException(OAuthErrorCode.MEMBER_NOT_FOUND));
    }

}
