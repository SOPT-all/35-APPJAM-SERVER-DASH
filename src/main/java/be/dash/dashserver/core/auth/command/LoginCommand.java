package be.dash.dashserver.core.auth.command;

import be.dash.dashserver.core.domain.member.SocialProvider;

public record LoginCommand(
        SocialProvider provider,
        String redirectUrl,
        String code) {
}
