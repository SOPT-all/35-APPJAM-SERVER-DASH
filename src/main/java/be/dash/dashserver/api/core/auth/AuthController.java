package be.dash.dashserver.api.core.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.auth.dto.LoginRequest;
import be.dash.dashserver.api.core.auth.dto.LoginResponse;
import be.dash.dashserver.api.core.auth.dto.ReissueResponse;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.LoginResult;
import be.dash.dashserver.core.auth.LoginService;
import be.dash.dashserver.core.auth.LogoutService;
import be.dash.dashserver.core.auth.ReissueService;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.auth.command.LoginCommand;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final ReissueService reissueService;
    private final LogoutService logoutService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResult result = loginService.login(new LoginCommand(request.provider(), request.redirectUrl(), request.code()));
        return ResponseEntity.ok(LoginResponse.of(result));
    }

    @Permission(role = {Role.MEMBER, Role.TEACHER})
    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        Token token = reissueService.reissue(refreshToken);
        return ResponseEntity.ok(ReissueResponse.of(token));
    }

    @Permission(role = {Role.MEMBER, Role.TEACHER})
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@MemberId Long memberId) {
        logoutService.logout(memberId);
        return ResponseEntity.noContent().build();
    }
}
