package be.dash.dashserver.api.core.auth;

import be.dash.dashserver.api.core.auth.dto.LoginRequest;
import be.dash.dashserver.api.core.auth.dto.LoginResponse;
import be.dash.dashserver.api.core.auth.dto.ReissueResponse;
import be.dash.dashserver.api.core.domain.member.MemberService;
import be.dash.dashserver.core.auth.LoginService;
import be.dash.dashserver.core.auth.ReissueService;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.auth.command.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final LoginService loginService;
    private final ReissueService reissueService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Token token = loginService.login(new LoginCommand(request.provider(), request.redirectUrl(), request.code()));
        //todo 온보딩 여부 확인
        return ResponseEntity.ok(LoginResponse.of(token));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@RequestHeader(AUTHORIZATION_HEADER) String refreshToken) {
        Token token = reissueService.reissue(refreshToken);
        return ResponseEntity.ok(ReissueResponse.of(token));
    }
}
