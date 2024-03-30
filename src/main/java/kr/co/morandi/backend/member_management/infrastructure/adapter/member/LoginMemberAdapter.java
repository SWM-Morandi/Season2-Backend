package kr.co.morandi.backend.member_management.infrastructure.adapter.member;

import kr.co.morandi.backend.member_management.application.port.out.member.CreateMemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.SocialType;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginMemberAdapter implements CreateMemberPort {

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
}
