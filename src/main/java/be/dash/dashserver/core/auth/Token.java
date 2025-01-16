package be.dash.dashserver.core.auth;

public record Token(
        String accessToken,
        String refreshToken
) {
}
