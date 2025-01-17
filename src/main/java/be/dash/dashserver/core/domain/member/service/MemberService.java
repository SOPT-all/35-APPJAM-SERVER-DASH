package be.dash.dashserver.core.domain.member.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.command.OnboardCommand;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void onboard(OnboardCommand command) {
        Member member = command.toMember();
        memberRepository.onboard(member);
    }
}
