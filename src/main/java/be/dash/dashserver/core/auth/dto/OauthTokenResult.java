package be.dash.dashserver.core.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthTokenResult(
        @JsonProperty("access_token")
        String accessToken
) {
}
