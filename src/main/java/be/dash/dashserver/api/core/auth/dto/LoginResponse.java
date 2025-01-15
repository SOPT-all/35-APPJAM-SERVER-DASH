package be.dash.dashserver.api.core.auth.dto;

import be.dash.dashserver.core.auth.Token;

public record LoginResponse(
        String accessToken,
        String refreshToken
//        boolean isOnboarded
) {
    public static LoginResponse of(Token token) {
        return new LoginResponse(token.accessToken(), token.refreshToken());
    }
}
