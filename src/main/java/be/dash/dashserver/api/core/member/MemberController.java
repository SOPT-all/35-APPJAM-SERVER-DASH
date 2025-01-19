package be.dash.dashserver.api.core.member;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.member.dto.MemberResponse;
import be.dash.dashserver.api.core.member.dto.OnBoardRequest;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.command.OnboardCommand;
import be.dash.dashserver.core.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    // 선생님은 온보딩 하지 못함
    // 멤버만 온보딩 가능
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

}
