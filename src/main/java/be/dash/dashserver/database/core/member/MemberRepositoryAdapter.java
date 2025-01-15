package be.dash.dashserver.database.core.member;

import org.springframework.stereotype.Component;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.exception.NotFoundException;
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
        memberJpaRepository.save(new MemberJpaEntity(member));
    }

    @Override
    public Member findById(long id) {
        return memberJpaRepository.findById(id).map(MemberJpaEntity::toDomain).orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
    }
}
