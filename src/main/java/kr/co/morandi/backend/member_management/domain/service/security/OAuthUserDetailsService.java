package kr.co.morandi.backend.member_management.domain.service.security;

import kr.co.morandi.backend.member_management.application.port.out.member.MemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.config.security.utils.OAuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthUserDetailsService implements UserDetailsService {

    private final MemberPort memberPort;
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberPort.findMemberById(Long.parseLong(memberId));
        return new OAuthDetails(memberId, member.getBaekjoonId());
    }
}
