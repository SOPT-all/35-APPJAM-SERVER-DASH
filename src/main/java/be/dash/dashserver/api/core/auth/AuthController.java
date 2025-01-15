package be.dash.dashserver.api.core.auth;

import be.dash.dashserver.api.core.auth.dto.LoginRequest;
import be.dash.dashserver.api.core.auth.dto.LoginResponse;
import be.dash.dashserver.api.core.domain.member.MemberService;
import be.dash.dashserver.core.auth.AuthService;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.auth.command.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Token token = authService.login(new LoginCommand(request.provider(), request.redirectUrl(), request.code()));
        return LoginResponse.of(token);
    }


}
