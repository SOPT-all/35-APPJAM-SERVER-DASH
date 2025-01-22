package be.dash.dashserver.api.core.member;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.member.dto.MemberResponse;
import be.dash.dashserver.api.core.member.dto.MyLessonDetailedResponse;
import be.dash.dashserver.api.core.member.dto.MyLessonsResponse;
import be.dash.dashserver.api.core.member.dto.OnBoardRequest;
import be.dash.dashserver.api.core.member.dto.ReservationDetailedResponse;
import be.dash.dashserver.api.core.member.dto.ReservationsResponse;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.command.OnboardCommand;
import be.dash.dashserver.core.domain.member.service.MemberService;
import be.dash.dashserver.core.domain.member.service.ReservationResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberFacade memberFacade;

    @Permission(role = Role.MEMBER)
    @PostMapping("/onboard")
    public ResponseEntity<Void> onboard(@MemberId Long memberId,
                                        @Valid @RequestBody OnBoardRequest request) {
        memberService.onboard(new OnboardCommand(memberId,
                request.name(),
                request.phoneNumber(),
                request.nickname(),
                request.level(),
                request.genres(),
                request.profileImageUrl()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMemberInformation(@MemberId Long memberId) {
        return ResponseEntity.ok(MemberResponse.from(memberService.getMemberInformation(memberId)));
    }

    @GetMapping("/me/reservations")
    public ResponseEntity<ReservationsResponse> getMemberReservations(@MemberId Long memberId) {
        List<ReservationResult> memberReservations = memberService.getMemberReservations(memberId);
        return ResponseEntity.ok(ReservationsResponse.from(memberReservations));
    }

    @GetMapping("/me/reservations/{reservationId}")
    public ResponseEntity<ReservationDetailedResponse> getReservation(@MemberId Long memberId, @PathVariable Long reservationId) {
        return ResponseEntity.ok(memberFacade.getMemberReservation(memberId, reservationId));
    }

    @Permission(role = Role.TEACHER)
    @GetMapping("/me/lessons")
    public ResponseEntity<MyLessonsResponse> getMemberLessons(@MemberId Long memberId) {
        return ResponseEntity.ok(memberFacade.getMemberLessons(memberId));
    }

    @Permission(role = Role.TEACHER)
    @GetMapping("/me/lessons/{lessonId}")
    public ResponseEntity<MyLessonDetailedResponse> getMemberLesson(@MemberId Long memberId, @PathVariable Long lessonId) {
        return ResponseEntity.ok(memberFacade.getMyLesson(memberId, lessonId));
    }

}
