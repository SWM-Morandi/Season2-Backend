package kr.co.morandi.backend.member_management.domain.service.member;

import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.SocialType;
import kr.co.morandi.backend.member_management.domain.model.oauth.TokenDto;
import kr.co.morandi.backend.member_management.domain.model.oauth.UserDto;
import kr.co.morandi.backend.member_management.application.config.oauth.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginService {

    private final JwtProvider jwtProvider;

    private final MemberPort memberPort;

    public TokenDto loginMember(UserDto userDto) {
        Member member = memberPort.findMemberByEmail(userDto.getEmail(), userDto.getType());
        Member savedMember = memberPort.saveMember(member);
        return jwtProvider.getTokens(savedMember);
    }
}
