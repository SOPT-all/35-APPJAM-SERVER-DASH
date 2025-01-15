package be.dash.dashserver.database.core.member;

import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {
    private final MemberJpaRepositoy memberJpaRepositoy;

    @Override
    public void save(Member member) {
        memberJpaRepositoy.save(new MemberJpaEntity(member));
    }
}
