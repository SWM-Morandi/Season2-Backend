package kr.co.morandi.backend.domain.oauth;

import kr.co.morandi.backend.config.security.OAuthDetails;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.global.exception.MorandiException;
import kr.co.morandi.backend.global.exception.errorcode.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new MorandiException(AuthErrorCode.MEMBER_NOT_FOUND));

        return new OAuthDetails(memberId, member.getBaekjoonId());
    }
}
