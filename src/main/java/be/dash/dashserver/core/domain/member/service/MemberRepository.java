package be.dash.dashserver.core.domain.member.service;

import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.SocialProvider;
import java.util.Optional;

public interface MemberRepository {
    AuthMember findBySocialIdAndProviderOrNull(String socialId, SocialProvider provider);
    AuthMember save(AuthMember authMember);
    void save(Member member);
}
