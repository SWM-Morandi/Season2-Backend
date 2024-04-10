package kr.co.morandi.backend.member_management.domain.service.member;

import kr.co.morandi.backend.member_management.application.config.oauth.JwtProvider;
import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.OAuthUserInfo;
import kr.co.morandi.backend.member_management.domain.model.oauth.response.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginService {

    private final JwtProvider jwtProvider;

    private final MemberPort memberPort;
    public AuthenticationToken loginMember(OAuthUserInfo oAuthUserInfo) {
        Optional<Member> maybeMember = memberPort.findMemberByEmail(oAuthUserInfo.getEmail());
        Member member = maybeMember.isPresent()
                ? maybeMember.get() : memberPort.saveMemberByEmail(oAuthUserInfo.getEmail(), oAuthUserInfo.getType());
        return jwtProvider.getAuthenticationToken(member);
    }
}
