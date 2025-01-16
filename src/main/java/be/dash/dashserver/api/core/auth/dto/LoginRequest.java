package be.dash.dashserver.api.core.auth.dto;

import be.dash.dashserver.core.domain.member.SocialProvider;

public record LoginRequest(
        SocialProvider provider,
        String redirectUrl,
        String code
) {
}
