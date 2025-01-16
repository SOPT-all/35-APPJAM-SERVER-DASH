package be.dash.dashserver.core;

import be.dash.dashserver.core.auth.Token;

public record LoginResult(
        String accessToken,
        String refreshToken,
        boolean isOnboarded
) {
    public static LoginResult of(Token token, boolean isOnboarded) {
        return new LoginResult(token.accessToken(), token.refreshToken(), isOnboarded);
    }
}
