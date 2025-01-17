package be.dash.dashserver.database.core.member;

import org.springframework.stereotype.Component;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.exception.NotFoundException;
import be.dash.dashserver.database.core.student.StudentGenreJpaEntity;
import be.dash.dashserver.database.core.student.StudentGenreJpaRepository;
import be.dash.dashserver.database.core.student.StudentJpaEntity;
import be.dash.dashserver.database.core.student.StudentJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final StudentGenreJpaRepository studentGenreJpaRepository;

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

    @Override
    public void onboard(Member member) {
        MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(member.getId()).orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
        memberJpaEntity.updateOnboardDetails(member);
        StudentJpaEntity studentJpaEntity = studentJpaRepository.save(StudentJpaEntity.builder()
                .profileImageUrl(member.getStudent().getProfileImageUrl())
                .level(member.getStudent().getLevel())
                .member(memberJpaEntity)
                .build());
        studentGenreJpaRepository.saveAll(member.getStudent().getGenres().stream().map(genre -> StudentGenreJpaEntity.builder()
                .student(studentJpaEntity)
                .genre(genre)
                .build()).toList());
    }
}
