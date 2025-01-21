package be.dash.dashserver.database.core.member;

import java.util.List;
import org.springframework.stereotype.Component;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.exception.NotFoundException;
import be.dash.dashserver.database.core.favorite.FavoriteJpaRepository;
import be.dash.dashserver.database.core.reservation.ReservationJpaRepository;
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
    private final ReservationJpaRepository reservationJpaRepository;
    private final FavoriteJpaRepository favoriteJpaRepository;

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
    public Member save(Member member) {
        MemberJpaEntity save = memberJpaRepository.save(new MemberJpaEntity(member));
        return Member.builder()
                .id(save.getId())
                .provider(member.getProvider())
                .socialId(member.getSocialId())
                .socialName(member.getSocialName())
                .role(member.getRole())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .student(member.getStudent())
                .build();
    }

    @Override
    public Member findById(long id) {
        return memberJpaRepository.findById(id).map(MemberJpaEntity::toDomain)
                .orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
    }

    @Override
    public void onboard(Member member) {
        MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(member.getId())
                .orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
        memberJpaEntity.updateOnboardDetails(member);
        StudentJpaEntity studentJpaEntity = studentJpaRepository.save(StudentJpaEntity.builder()
                .profileImageUrl(member.getStudent().getProfileImageUrl())
                .level(member.getStudent().getLevel())
                .member(memberJpaEntity)
                .build());
        studentGenreJpaRepository.saveAll(member.getStudent().getGenres().stream()
                .map(genre -> StudentGenreJpaEntity.builder()
                        .student(studentJpaEntity)
                        .genre(genre)
                        .build()).toList());
    }

    @Override
    public Student findStudentByMemberId(long memberId) {
        StudentJpaEntity studentJpaEntity = studentJpaRepository.findStudentsByMemberIdWithMember(memberId)
                .orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
        List<StudentGenreJpaEntity> studentGenreJpaEntities = studentGenreJpaRepository.findAllStudentGenresWithStudent(studentJpaEntity.getId());
        return studentJpaEntity.toDomain(studentGenreJpaEntities);
    }

    @Override
    public int getReservationCountByStudentId(Long studentId) {
        return reservationJpaRepository.countByStudentId(studentId);
    }

    @Override
    public int getFavoriteCountByStudentId(Long studentId) {
        return favoriteJpaRepository.countByStudentId(studentId);
    }

    @Override
    public void updateRole(Long id, Role role) {
        memberJpaRepository.updateRole(id, role);
    }
}
