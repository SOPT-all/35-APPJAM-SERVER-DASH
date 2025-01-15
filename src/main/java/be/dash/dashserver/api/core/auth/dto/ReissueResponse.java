package be.dash.dashserver.api.core.auth.dto;

import be.dash.dashserver.core.auth.Token;

public record ReissueResponse(
        String accessToken,
        String refreshToken
) {
    public static ReissueResponse of(Token token) {
        return new ReissueResponse(token.accessToken(), token.refreshToken());
    }
}
