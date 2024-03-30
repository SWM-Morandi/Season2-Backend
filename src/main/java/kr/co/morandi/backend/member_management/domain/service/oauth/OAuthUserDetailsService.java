package kr.co.morandi.backend.member_management.domain.service.oauth;

import kr.co.morandi.backend.member_management.application.port.out.oauth.FindMemberPort;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.domain.model.oauth.OAuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthUserDetailsService implements UserDetailsService {

    private final FindMemberPort findMemberPort;
    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = findMemberPort.findMember(Long.parseLong(memberId));
        return new OAuthDetails(memberId, member.getBaekjoonId());
    }
}
