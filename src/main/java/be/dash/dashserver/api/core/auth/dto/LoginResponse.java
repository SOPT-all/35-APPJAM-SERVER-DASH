package be.dash.dashserver.api.core.auth.dto;

import be.dash.dashserver.core.auth.dto.LoginResult;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        boolean isOnboarded
) {
    public static LoginResponse of(LoginResult result) {
        return new LoginResponse(result.accessToken(), result.refreshToken(), result.isOnboarded());
    }
}
