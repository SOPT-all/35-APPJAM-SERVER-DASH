package be.dash.dashserver.api.core.teacher.dto;

import be.dash.dashserver.core.auth.Token;

public record CreateTeacherResponse(String accessToken,
                                    String refreshToken) {
    public static CreateTeacherResponse from(Token token) {
        return new CreateTeacherResponse(token.accessToken(), token.refreshToken());
    }
}
