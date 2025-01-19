package be.dash.dashserver.core.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.command.OnboardCommand;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public void onboard(OnboardCommand command) {
        Member member = command.toMember();
        memberRepository.onboard(member);
    }

    @Transactional(readOnly = true)
    public MemberInformationResult getMemberInformation(Long memberId) {
        Member member = memberRepository.findById(memberId);
        Student student = memberRepository.findStudentByMemberId(memberId);
        return new MemberInformationResult(
                member.getNickname(),
                student.getProfileImageUrl(),
                memberRepository.getReservationCountByStudentId(student.getId()),
                memberRepository.getFavoriteCountByStudentId(memberId),
                lessonRepository.getLessonCount(memberId)
        );
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
