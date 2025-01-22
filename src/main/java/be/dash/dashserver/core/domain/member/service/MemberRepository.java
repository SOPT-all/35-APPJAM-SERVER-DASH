package be.dash.dashserver.core.domain.member.service;

import java.util.List;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.Student;

public interface MemberRepository {
    AuthMember findBySocialIdAndProviderOrNull(String socialId, SocialProvider provider);

    AuthMember save(AuthMember authMember);

    Member save(Member member);

    Member findById(long id);

    void onboard(Member member);

    Student findStudentByMemberId(long memberId);

    int getReservationCountByStudentId(Long studentId);

    int getFavoriteCountByStudentId(Long studentId);

    void updateRole(Long id, Role role);

    List<Member> findAllByIds(List<Long> memberIds);
}
