package be.dash.dashserver.database.core.member;

import org.springframework.stereotype.Component;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public AuthMember findBySocialIdAndProviderOrNull(String socialId, SocialProvider provider) {
        return memberJpaRepository.findBySocialIdAndProvider(socialId, provider)
                .map(MemberJpaEntity::toAuthMember)
                .orElseGet(() -> null);
    }

    @Override
    public AuthMember save(AuthMember authMember) {
        return memberJpaRepository.save(MemberJpaEntity.fromDomain(authMember)).toAuthMember();
    }

    @Override
    public void save(Member member) {
        memberJpaRepositoy.save(new MemberJpaEntity(member));
    }
}
