package be.dash.dashserver.core.domain.member.service;

import be.dash.dashserver.core.domain.member.Member;

public interface MemberRepository {
    void save(Member member);
}
