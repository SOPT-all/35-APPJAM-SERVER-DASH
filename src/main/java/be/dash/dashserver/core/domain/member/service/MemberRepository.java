package be.dash.dashserver.core.domain.member.service;

import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.Student;

public interface MemberRepository {
    AuthMember findBySocialIdAndProviderOrNull(String socialId, SocialProvider provider);

    AuthMember save(AuthMember authMember);

    Member save(Member member);

    Member findById(long id);

    void onboard(Member member);

    Student findStudentByMemberId(long memberId);
}
