package kr.co.morandi.backend.domain.member.service.oauth;

import kr.co.morandi.backend.config.security.JwtProvider;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.member.oauth.TokenDto;
import kr.co.morandi.backend.domain.member.oauth.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    public TokenDto loginOrRegisterMember(UserDto userDto) {
        Member member = memberRepository.findByEmail(userDto.getEmail())
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .email(userDto.getEmail())
                                .socialType(userDto.getType())
                                .build()
                )
        );

        return jwtProvider.getTokens(member);
    }
}
